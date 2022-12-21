package main

import (
	"github.com/gofiber/fiber/v2"

	"io/ioutil"
	"log"
	"net/http"
)

func main() {
	app := fiber.New()
	//app.Use(logger.New())

	app.Get("/", func(c *fiber.Ctx) error {

		resp, err := http.Get("http://172.17.0.1:9090/test.txt")
		if err != nil {
			log.Fatal("Error getting response. ", err)
		}
		defer resp.Body.Close()

		body, err := ioutil.ReadAll(resp.Body)
		if err != nil {
			log.Fatal("Error reading response. ", err)
		}
		return c.Send(body)
	})
	app.Listen(":8080")

}
