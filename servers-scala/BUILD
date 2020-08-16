scala_library(
  sources=globs("*.scala"),
  dependencies=[
    '3rdparty/jvm:twitter-finagle-http',
  ]
)

jvm_binary(
  name='basic',
  main='servers.Basic',
  dependencies=[
    ":servers"
  ]
)
