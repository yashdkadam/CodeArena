"use client";

import { useRouter } from "next/navigation";
import JoinScreen from "@/app/components/JoinScreen";

export default function Player2() {
  const router = useRouter();

  const handleJoin = async (username: string) => {
    const res = await fetch("http://localhost:8081/game/join", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username }),
    });

    const data = await res.json();

    localStorage.setItem("playerId", data.player2?.id);
    localStorage.setItem("sessionId", data.id);

    router.push(`/game/${data.id}`);
  };

  return <JoinScreen onJoin={handleJoin} />;
}
