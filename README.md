# Garook
Scala library to easily and efficiently process large XMLs with repetatively occuring elements.
The library is using Stax2 to parse and stream xml.

## Quickstart

Following example demonstrates usage with support of [scala-xml](https://github.com/scala/scala-xml) to read repetative elements.

Example xml:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<breakfast_menu>
    <food>
        <name>Belgian Waffles</name>
        <price>$5.95</price>
    </food>
    <food>
        <name>Strawberry Belgian Waffles</name>
        <price>$7.95</price>
    </food>
</breakfast_menu>
```
Scala:
```scala
    import scala.util.Using
    import scala.jdk.CollectionConverters._
    
    val xmlInputStream: InputStream = ???
    val names = Using(new Garook().parse(xmlInputStream, new ExactLocalPathMatcher("breakfast_menu.food"))) { iterator =>
      iterator.asScala.map(elem => XML.load(new ByteArrayInputStream(elem)))
        .map(_ \\ "name")
        .map(_.text)
        .toList
    }.get
    
    println(names.mkString(", "))

```

prints
```
Belgian Waffles, Strawberry Belgian Waffles
```
