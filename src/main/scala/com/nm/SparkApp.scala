package com.nm
import org.apache.spark.{SparkContext,SparkConf}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql._
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.{Vectors, Vector}



object SparkApp {
  /*
  The basic import neccessary to access hive data from the spark shell are:
    import org.apache.spark.sql.hive.HiveContext
    val hiveContext = new HiveContext(sc)
    import hiveContext.implicits._
    import hiveContext.sql

     */

  //Spark Boilerpalte

  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("Kmeans")
    val sc = new SparkContext(sparkConf)
    val hiveContext = new HiveContext(sc)
    import hiveContext.implicits._
    import hiveContext.sql

    ////////////////
    //Feature Engineering

    case class Iris(ix: String
                    , S_Lenght: Double
                    , S_Width: Double
                    , P_Length: Double
                    , P_Width: Double
                    , Species: String)

    val data = sc.textFile("iris.csv").map(_.split(","))
      .map(x => Iris(x(0), x(1).toDouble, x(2).toDouble, x(3).toDouble, x(4).toDouble, x(5)))

    val numericData = data.map(x => ((x.ix, x.Species), Vectors.dense(x.S_Lenght, x.S_Width, x.P_Length, x.P_Width)))


    ////////////////
    //Training Splits
    val splits = numericData.randomSplit(Array(0.6, 0.4), seed = 11L)
    val training = splits(0).map(_._2)
    val test = splits(1)

    ///////////////
    //Learning
    val model = KMeans.train(training, 3, 20)

    val fit = test map {
      case ((ix, s), vectors) =>
        val pred = model.predict(vectors)
        val clusterCenters = model.clusterCenters
        //code to calculate distance ffrmo pred -> cluster cents
        (ix, s, pred)
    }
    //////////////
    //Evaluate

    fit.groupBy{ case (a,b,c) => (b,c) }.map(x => (x._1, x._2.size))
      .collect
      .foreach(println)





  }


}


