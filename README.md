# garook
Scala library to easily and efficiently process large XMLs with repetatively occuring elements.
The library is using Stax2 to parse and stream xml.

Following example demonstrated usage of the library with support of [scala-xml](https://github.com/scala/scala-xml) to read repetative elements.

## Quickstart
Example xml:
```
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
```
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
