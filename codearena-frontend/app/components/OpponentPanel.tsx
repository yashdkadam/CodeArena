"use client";

export default function OpponentPanel({ winner }: any) {
  const myId = Number(localStorage.getItem("playerId"));

  return (
    <div className="p-4 bg-black text-white h-full">
      <h2 className="text-lg">Game Status</h2>

      {!winner && <p className="mt-2">Waiting for submissions...</p>}

      {winner && (
        <div className="mt-4 text-xl">
          {winner === myId ? "🎉 You Won!" : "❌ You Lost!"}
        </div>
      )}
    </div>
  );
}
