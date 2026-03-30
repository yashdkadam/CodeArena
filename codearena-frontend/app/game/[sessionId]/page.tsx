"use client";

import { useParams } from "next/navigation";
import { useEffect, useRef, useState } from "react";
import GameLayout from "@/app/components/GameLayout";
import WaitingScreen from "@/app/components/WaitingScreen";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

export default function GamePage() {
  const params = useParams();
  const sessionId = params.sessionId as string;

  const [started, setStarted] = useState(false);
  const [winner, setWinner] = useState<number | null>(null);
  const [problem, setProblem] = useState<any>(null);

  const clientRef = useRef<Client | null>(null);

  // ✅ WebSocket (stable connection)
  useEffect(() => {
    if (clientRef.current) return; // prevent multiple connections

    const client = new Client({
      webSocketFactory: () => new SockJS("http://localhost:8081/ws"),
      reconnectDelay: 5000,

      onConnect: () => {
        console.log("WS Connected:", sessionId);

        client.subscribe(`/topic/game/${sessionId}`, (msg) => {
          const data = JSON.parse(msg.body);

          console.log("WS Event:", data);

          if (data.event === "GAME_STARTED") {
            setStarted(true);
            setProblem(data.problem);
          }

          if (data.event === "GAME_FINISHED") {
            setWinner(data.winner);
          }
        });
      },
    });

    client.activate();
    clientRef.current = client;

    return () => {
      client.deactivate();
      clientRef.current = null;
    };
  }, []); // ✅ important

  // ✅ Polling fallback (VERY IMPORTANT)
  useEffect(() => {
    const interval = setInterval(async () => {
      try {
        const res = await fetch(`http://localhost:8081/game/${sessionId}`);
        const data = await res.json();
        console.log(data);

        if (data.status === "STARTED" && data.problem) {
          setStarted(true);
          setProblem(data.problem);
        }

        if (data.status === "FINISHED") {
          setWinner(data.winnerId);
        }
      } catch (err) {
        console.error("Polling error:", err);
      }
    }, 2000);

    return () => clearInterval(interval);
  }, [sessionId]);

  if (!started || !problem) return <WaitingScreen />;

  return (
    <GameLayout
      sessionId={sessionId}
      winner={winner}
      problem={problem}
    />
  );
}