"use client";

import Editor from "@monaco-editor/react";
import { useState } from "react";

export default function CodeEditor({ sessionId, problem }: any) {
  const [code, setCode] = useState(`function solve(input) {
  let [a, b] = input.split(" ").map(Number);
  console.log(a + b);
}`);

  const [output, setOutput] = useState("");

  const runCode = () => {
    return new Promise<string>((resolve) => {
      const iframe = document.createElement("iframe");

      iframe.style.display = "none";

      // ✅ SAFE sandbox (no same-origin access needed)
      iframe.sandbox = "allow-scripts";

      document.body.appendChild(iframe);

      const script = `
        let output = "";

        const console = {
          log: (...args) => {
            output += args.join(" ") + "\\n";
          }
        };

        const sendResult = (res) => {
          parent.postMessage(res, "*");
        };

        // Timeout protection (2 sec)
        const timeout = setTimeout(() => {
          sendResult("Error: Execution timeout");
        }, 2000);

        try {
          ${code}
          solve(\`${problem?.input || ""}\`);
          clearTimeout(timeout);
          sendResult(output.trim());
        } catch (err) {
          clearTimeout(timeout);
          sendResult("Error: " + err);
        }
      `;

      const listener = (event: MessageEvent) => {
        resolve(event.data);
        window.removeEventListener("message", listener);
        document.body.removeChild(iframe);
      };

      window.addEventListener("message", listener);

      // ✅ FIX: Use srcdoc instead of contentDocument
      iframe.srcdoc = `<script>${script}<\/script>`;
    });
  };

  const handleRun = async () => {
    const result = await runCode();
    setOutput(result);
  };

  const submit = async () => {
    const playerId = localStorage.getItem("playerId");

    const result = await runCode();
    setOutput(result);

    await fetch("http://localhost:8081/submit", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        sessionId,
        playerId,
        output: result,
      }),
    });
  };

  return (
    <div className="h-full flex flex-col">
      {/* Top Bar */}
      <div className="flex justify-between p-2 bg-gray-800 text-white">
        <button onClick={handleRun} className="bg-gray-600 px-3 py-1 rounded">
          Run
        </button>

        <button onClick={submit} className="bg-green-600 px-3 py-1 rounded">
          Submit
        </button>
      </div>

      {/* Editor */}
      <div className="flex-1">
        <Editor
          height="100%"
          theme="vs-dark"
          defaultLanguage="javascript"
          value={code}
          onChange={(val) => setCode(val || "")}
        />
      </div>

      {/* Output Panel */}
      <div className="h-32 bg-black text-green-400 p-2 overflow-auto text-sm">
        <div className="font-bold mb-1">Output:</div>
        <pre>{output || "No output yet..."}</pre>
      </div>
    </div>
  );
}
