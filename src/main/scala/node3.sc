package nodescala


import math.random
import scala.util.{Try, Success, Failure}

object node3 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  abstract class Coin {
     val denomination: Int
  }
    case class Silver() extends Coin {
      val denomination = 1
    }
    case class Gold() extends Coin {
      val denomination = 10
    }
    
  abstract class Treasure{
    val value: Int
    }
  
  trait Adventure {
    def collectCoins(): Try[List[Coin]]
    def buyTreasure(coins: List[Coin]): Try[Treasure]
  }
 
  def eatenByMonster(a:Adventure) = (random < 0.1)//> eatenByMonster: (a: nodescala.node3.Adventure)Boolean
  class GameOverException(msg: String) extends Error
  val treasureCost = 50                           //> treasureCost  : Int = 50
  
  object Diamond extends Treasure {
    val value = treasureCost
    override def toString = "Diamond"
  }
   
  def coinSource(rand: Double, prob: Double ): Coin =
    if (rand < prob) {
      Thread.sleep(1000)
      new Gold
    }
    else {
      Thread.sleep(100)
      new Silver
    }                                             //> coinSource: (rand: Double, prob: Double)nodescala.node3.Coin
  
  object Adventure {
    def apply() = new Adventure {
       def collectCoins(): Try[List[Coin]] = Try {
         if (eatenByMonster(this))
           throw(new GameOverException("Oooops"))
         else for { i <- 1 to 10 toList } yield coinSource(random, 0.5)
       }
       def totalCoins(coins: List[Coin]) =
         coins.foldLeft(0)( (sum, coin) => sum + coin.denomination  )
       
       def buyTreasure(coins: List[Coin]): Try[Treasure] = Try
       {
         if (totalCoins(coins) < treasureCost)
           throw(new GameOverException("Nice try!"))
         else
           Diamond
       }
    }
  }

  val adventure = Adventure()                     //> adventure  : nodescala.node3.Adventure{def totalCoins(coins: List[nodescala
                                                  //| .node3.Coin]): Int} = nodescala.node3$$anonfun$main$1$Adventure$3$$anon$1@7
                                                  //| d3e1384
  val coins: Try[List[Coin]] = adventure.collectCoins()
                                                  //> coins  : scala.util.Try[List[nodescala.node3.Coin]] = Success(List(Gold(), 
                                                  //| Gold(), Silver(), Silver(), Gold(), Silver(), Silver(), Silver(), Gold(), S
                                                  //| ilver()))
  val treasure: Try[Treasure] = coins.flatMap(cs=>{adventure.buyTreasure(cs)})
                                                  //> treasure  : scala.util.Try[nodescala.node3.Treasure] = Failure(nodescala.no
                                                  //| de3$$anonfun$main$1$GameOverException$1)

   
}