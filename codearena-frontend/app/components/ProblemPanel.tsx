export default function ProblemPanel({ problem }: any) {
  if (!problem) return <div className="p-4">Loading...</div>;

  return (
    <div className="p-4 overflow-y-auto h-full">
      <h1 className="text-xl font-bold">{problem.title}</h1>

      <p className="mt-2">{problem.description}</p>

      <pre className="bg-black-100 p-2 mt-4">
        Input:{"\u00A0"}
        {problem.input}
        <br />
        Output:{"\u00A0"}
        {problem.output}
      </pre>
    </div>
  );
}
