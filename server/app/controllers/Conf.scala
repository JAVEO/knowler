package controllers

import play.api.Configuration

trait Conf {
  def googleClientId = configParam("google.client.id")
  def googleClientSecret = configParam("google.client.secret")
  def googleDriveUrl = configParam("google.drive.url")
  def googleTokenUrl = configParam("google.token.url")

  def configParam(key: String) =
    configuration.getString(key)
      .getOrElse(throw new RuntimeException("No config param with key " + key))

  def configuration: Configuration
}
