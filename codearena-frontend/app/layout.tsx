import "./globals.css";
import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "My App",
  description: "Built with Next.js",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body>
        <header className="p-4 shadow-md bg-white">
          <nav className="flex justify-between items-center max-w-6xl mx-auto">
            <h1 className="text-xl font-bold">MyApp</h1>
            <div className="space-x-4">
              <a href="/">Home</a>
              <a href="/about">About</a>
              <a href="/contact">Contact</a>
            </div>
          </nav>
        </header>

        <main className="max-w-6xl mx-auto p-4">{children}</main>

        <footer className="p-4 text-center border-t mt-8">
          © {new Date().getFullYear()} MyApp. All rights reserved.
        </footer>
      </body>
    </html>
  );
}
