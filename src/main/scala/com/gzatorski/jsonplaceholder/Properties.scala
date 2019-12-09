package com.gzatorski.jsonplaceholder

import java.io.File

import com.typesafe.config.{Config, ConfigFactory}

class Properties(config: Config) {

  val apiProtocol: String = config.getString("api.protocol")
  val apiAddress: String = config.getString("api.address")
  val apiEndpoint: String = config.getString("api.endpoint")
  val apiRequestAddress = s"${clean(apiProtocol)}://${clean(apiAddress)}/${clean(apiEndpoint)}"

  val downloadMaxPosts: Int = config.getInt("download.maxposts")
  val downloadDirectory: String = config.getString("download.directory")
  val filesOverwite: Boolean = config.getBoolean("download.overwrite")


  private def clean(property: String) = {
    property
      .trim
      .replace("/", "")
      .replace(":", "")
  }

}


object Properties {

  def apply(path: String): Properties = {
    val baseConfig = ConfigFactory.load()
    val config = ConfigFactory.parseFile(new File(path)).withFallback(baseConfig)

    new Properties(config)
  }

}