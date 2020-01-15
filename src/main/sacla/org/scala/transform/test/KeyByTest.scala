package org.scala.transform.test

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, _}

object KeyByTest {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val dataStream: DataStream[(String, Int)] = env.fromElements(("a", 1), ("c", 2), ("a", 4),("a",5))
    val result = dataStream.keyBy(0).max(1)
    result.print()
    env.execute("test")
  }
}
