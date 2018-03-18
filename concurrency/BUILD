scala_library(
  name='hello',
  sources='Hello.scala',
  dependencies=[
    '3rdparty/jvm:twitter-util',
  ]
)

jvm_binary(
  name='hello-bin',
  main='concurrency.HelloConcurrency',
  dependencies=[
    ':hello'
  ]
)
