import sbt._

object MicroServiceBuild extends Build with MicroService {
  val appName = "address-lookup"

  override lazy val appDependencies: Seq[ModuleID] = AppDependencies()
}

private object AppDependencies {
  import play.sbt.PlayImport._
  import play.core.PlayVersion

  private val hmrcTestVersion = "3.5.0-play-25"
  private val scalaTestVersion = "2.2.6"
  private val pegdownVersion = "1.6.0"
  private val jacksonVersion = "2.8.9"

  val compile = Seq(
    ws,
    "uk.gov.hmrc" %% "bootstrap-play-25" % "5.4.0",
    "uk.gov.hmrc" %% "domain" % "5.10.0-play-25",
    "uk.gov.hmrc" %% "logging" % "0.7.0" withSources(),
    "uk.gov.hmrc" %% "address-reputation-store" % "2.40.0" withSources(),
    "com.univocity" % "univocity-parsers" % "1.5.6",
    "com.fasterxml.jackson.core" % "jackson-core" % jacksonVersion,
    "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion,
    "com.fasterxml.jackson.core" % "jackson-annotations" % jacksonVersion,
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion,
    "com.sksamuel.elastic4s" %% "elastic4s-core" % "2.4.0",
    "com.github.tototoshi" %% "scala-csv" % "1.3.6"
  )

  trait TestDependencies {
    lazy val scope: String = "test"
    lazy val test : Seq[ModuleID] = ???
  }

  object Test {
    def apply(): Seq[ModuleID] = new TestDependencies {
      override lazy val test = Seq(
        "uk.gov.hmrc" %% "hmrctest" % hmrcTestVersion % scope,
        "org.scalatest" %% "scalatest" % scalaTestVersion % scope,
        "org.pegdown" % "pegdown" % pegdownVersion % scope,
        "com.typesafe.play" %% "play-test" % PlayVersion.current % scope,
        "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % scope,
        "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % scope,
        "org.jsoup" % "jsoup" % "1.7.3" % scope,
        "org.mockito" % "mockito-all" % "1.10.19" % scope,
        "org.elasticsearch" % "elasticsearch" % "2.4.1" % scope
      )
    }.test
  }

  object IntegrationTest {
    def apply(): Seq[ModuleID] = new TestDependencies {

      override lazy val scope: String = "it"

      override lazy val test = Seq(
        "uk.gov.hmrc" %% "hmrctest" % hmrcTestVersion % scope,
        "org.scalatest" %% "scalatest" % scalaTestVersion % scope,
        "org.pegdown" % "pegdown" % pegdownVersion % scope,
        "com.typesafe.play" %% "play-test" % PlayVersion.current % scope,
        "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % scope,
        "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % scope,
        "org.jsoup" % "jsoup" % "1.7.3" % scope,
        "org.mockito" % "mockito-all" % "1.10.19" % scope,
        "org.elasticsearch" % "elasticsearch" % "2.4.1" % scope
      )
    }.test
  }

  def apply(): Seq[ModuleID] = compile ++ Test() ++ IntegrationTest()
}

