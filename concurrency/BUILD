scala_library(
  sources=globs('*.scala'),
  dependencies=[
    '3rdparty/jvm:twitter-util',
  ]
)

jvm_binary(
  name='hello-bin',
  main='concurrency.HelloConcurrency',
  dependencies=[
    ':concurency'
  ]
)

jvm_binary(
  name='doinorder-bin',
  main='concurrency.DoInOrder',
  dependencies=[
    ':concurrency'
  ]
)
  
jvm_binary(
  name='sequential-bin',
  main='concurrency.Sequential',
  dependencies=[
    ':concurrency'
  ]
)
