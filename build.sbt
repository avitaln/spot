enablePlugins(ScalaJSPlugin)
scalaVersion in Global := "2.12.8"
name := "spot"

//updateOptions := updateOptions.value.withLatestSnapshots(false)
//resolvers += Resolver.sonatypeRepo( "snapshots" )
//resolvers += "Artifactory" at "http://repo.dev.wixpress.com/artifactory/libs-snapshots/"

libraryDependencies += "com.lihaoyi" %%% "upickle" % "0.7.1"
libraryDependencies += "com.lihaoyi" %%% "ujson" % "0.7.1"
libraryDependencies += "com.thoughtworks.binding" %%% "dom" % "11.6.0"
libraryDependencies += "com.thoughtworks.binding" %%% "route" % "11.6.0"

scalacOptions += "-P:scalajs:sjsDefinedByDefault"
scalaJSUseMainModuleInitializer := true
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)

