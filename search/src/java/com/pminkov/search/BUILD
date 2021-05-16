jvm_binary(
    source = 'Search.java',
    main = 'com.pminkov.search.Search',
    dependencies = [
        ':lib'
    ]
)

java_library(
    name='lib',
    sources=['**/*.java'],
    dependencies = [
        'search/3rdparty/jvm:lucene-core',
        'search/3rdparty/jvm:lucene-queryparser',
        'search/3rdparty/jvm:lucene-analyzers-common',
        'search/3rdparty/jvm:google-guava'
    ]
)
