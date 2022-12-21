import { serve } from "https://deno.land/std/http/server.ts";

async function handler(req: Request): Promise<Response> {
  const resp = await fetch("http://172.17.0.1:9090/test.txt", {
    headers: {
    },
  });
  return new Response(resp.body, {
    status: resp.status,
    headers: {
    },
  });
}

serve(handler);
