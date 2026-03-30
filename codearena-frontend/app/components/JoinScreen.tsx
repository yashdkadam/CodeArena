"use client";

import { useState } from "react";

export default function JoinScreen({ onJoin }: any) {
  const [username, setUsername] = useState("");

  return (
    <div className="h-screen flex flex-col items-center justify-center gap-4">
      <h1 className="text-2xl font-bold">Enter Username</h1>

      <input
        className="border p-2"
        placeholder="username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />

      <button
        className="bg-blue-600 text-white px-4 py-2"
        onClick={() => onJoin(username)}
      >
        Join Game
      </button>
    </div>
  );
}
