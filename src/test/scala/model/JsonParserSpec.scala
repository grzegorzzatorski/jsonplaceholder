package com.gzatorski.jsonplaceholder.model

import org.scalatest.{Matchers, WordSpecLike}

class JsonParserSpec extends Matchers with WordSpecLike {

  "JSONParser" should {

    "properly parse single element list" in {
      val response =
        """[
          |  {
          |    "userId": 1,
          |    "id": 1,
          |    "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
          |    "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
          |  }
          | ]""".stripMargin

      val expected = List(Post(1,
        1,
        "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
        "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto")
      )

      JsonParser.getAsPosts(response) should equal(expected)
    }

    "properly parse multiple element list" in {
      val response =
        """[
          |  {
          |    "userId": 1,
          |    "id": 1,
          |    "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
          |    "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
          |  },
          |  {
          |    "userId": 1,
          |    "id": 2,
          |    "title": "qui est esse",
          |    "body": "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
          |  },
          |  {
          |    "userId": 1,
          |    "id": 3,
          |    "title": "ea molestias quasi exercitationem repellat qui ipsa sit aut",
          |    "body": "et iusto sed quo iure\nvoluptatem occaecati omnis eligendi aut ad\nvoluptatem doloribus vel accusantium quis pariatur\nmolestiae porro eius odio et labore et velit aut"
          |  }
          |]""".stripMargin

      val expected = List(
        Post(1,
          1,
          "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
          "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
        ),
        Post(
          1,
          2,
          "qui est esse",
          "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
        ),
        Post(
          1,
          3,
          "ea molestias quasi exercitationem repellat qui ipsa sit aut",
          "et iusto sed quo iure\nvoluptatem occaecati omnis eligendi aut ad\nvoluptatem doloribus vel accusantium quis pariatur\nmolestiae porro eius odio et labore et velit aut"
        )
      )

      JsonParser.getAsPosts(response) should equal(expected)
    }

    "properly parse empty list" in {
      val response ="[]"
      val expected = List()
      JsonParser.getAsPosts(response) should equal(expected)
    }

    "properly parse empty response at all" in {
      val response =""
      val expected = List()
      JsonParser.getAsPosts(response) should equal(expected)
    }

  }


}
