scala_library(
  sources=globs('*.scala'),
  dependencies=[
    '3rdparty/jvm:twitter-util',
  ]
)

jvm_binary(
  name="runner",
  main="concurrency.Runner",
  dependencies=[
    ":concurrency"
  ]
)

