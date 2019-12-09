package com.gzatorski.jsonplaceholder

import org.scalatest.{Matchers, WordSpecLike}


class PropertiesSpec extends Matchers with WordSpecLike {

  val configPath = getClass.getResource("/application.conf").getPath
  val props = Properties(configPath)


  "Properties" should {

    "load all values correctly when the config file exists" in {

      props.apiAddress should equal("jsonplaceholder.typicode.com")
      props.apiEndpoint should equal("posts")
      props.apiProtocol should equal("https")
      props.downloadMaxPosts should equal(100)
      props.downloadDirectory should equal("downloadedPosts")

    }

    "properly build api request address" in {

      props.apiRequestAddress should equal("https://jsonplaceholder.typicode.com/posts")

    }

    "properly build api request address when properties has wrong values" in {

      val configPath = getClass.getResource("/application-broken.conf").getPath
      val props = Properties(configPath)

      props.apiRequestAddress should equal("https://jsonplaceholder.typicode.com/posts")

    }

  }

}
