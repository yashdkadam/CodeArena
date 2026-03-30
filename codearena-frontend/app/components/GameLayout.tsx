"use client";

import { PanelGroup, Panel, PanelResizeHandle } from "react-resizable-panels";
import ProblemPanel from "./ProblemPanel";
import CodeEditor from "./CodeEditor";
import OpponentPanel from "./OpponentPanel";

export default function GameLayout({ sessionId, winner, problem }: any) {
  return (
    <div className="h-screen">

      <PanelGroup direction="horizontal">

        <Panel defaultSize={50}>
          <ProblemPanel problem={problem} />
        </Panel>

        <PanelResizeHandle className="w-1 bg-gray-700" />

        <Panel defaultSize={50}>
          <PanelGroup direction="vertical">

            <Panel defaultSize={70}>
              <CodeEditor sessionId={sessionId} problem={problem} />
            </Panel>

            <PanelResizeHandle className="h-1 bg-gray-700" />

            <Panel defaultSize={30}>
              <OpponentPanel winner={winner} />
            </Panel>

          </PanelGroup>
        </Panel>

      </PanelGroup>
    </div>
  );
}