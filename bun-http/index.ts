Bun.serve({
  async fetch(req) {
    const resp = await fetch("http://172.17.0.1:9090/test.txt", {
      headers: {
      },
    });
    return new Response(resp.body, {
      status: resp.status,
      headers: {
      },
    });
  },
  error(error: Error) {
    return new Response("Error: " + error.toString(), { status: 500 });
  },
  port: 8080,
});
