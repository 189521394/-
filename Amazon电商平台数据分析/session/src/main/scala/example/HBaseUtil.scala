package example

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.client.{Admin, ColumnFamilyDescriptor, ColumnFamilyDescriptorBuilder, Connection, ConnectionFactory, Get, Put, Result, Table, TableDescriptor, TableDescriptorBuilder}
import org.apache.hadoop.hbase.util.Bytes

import scala.collection.JavaConverters._

case class Info(id: String, view: Long, cart: Long, purchase: Long)
case class Product(addName: String, pro5115: Long, pro4856: Long, pro4767: Long)
case class conBean(jump: String, conv: Double)

class HBaseUtil {
    var conn: Connection = _

    def init(): Unit = {
        val conf: Configuration = HBaseConfiguration.create()
        conf.set("hbase.zookeeper.quorum", "hadoop1:2181,hadoop2:2181,hadoop3:2181")
        conn = ConnectionFactory.createConnection(conf)
    }

    def createTable(name: String): Unit = {
        val admin: Admin = conn.getAdmin
        val tableName: TableName = TableName.valueOf(name)

        // Check if table already exists, drop it if so
        if (admin.tableExists(tableName)) {
            admin.disableTable(tableName)
            admin.deleteTable(tableName)
        }

        val tdb: TableDescriptorBuilder = TableDescriptorBuilder.newBuilder(tableName)
        val cfdb: ColumnFamilyDescriptorBuilder = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes("info"))
        val cfd: ColumnFamilyDescriptor = cfdb.build()
        tdb.setColumnFamily(cfd)
        val td: TableDescriptor = tdb.build()
        admin.createTable(td)
        admin.close()
    }

    /**
     * 将 Amazon 聚合结果批量写入 HBase categoryCount 表
     * 表结构：rowKey=category_id, 列族=info, 列=view/cart/purchase
     * @param infoList 按 category_id 聚合的 view/cart/purchase 计数列表
     */
    def infoPut(name: String, infoList: Seq[Info]): Unit = {
        if (infoList.isEmpty) return

        val table: Table = conn.getTable(TableName.valueOf(name))
        try {
            val cf: Array[Byte] = Bytes.toBytes("info")

            val puts: java.util.List[Put] = infoList.map { info =>
                val put: Put = new Put(Bytes.toBytes(info.id))
                put.addColumn(cf, Bytes.toBytes("view"), Bytes.toBytes(info.view))
                put.addColumn(cf, Bytes.toBytes("cart"), Bytes.toBytes(info.cart))
                put.addColumn(cf, Bytes.toBytes("purchase"), Bytes.toBytes(info.purchase))
                put
            }.asJava

            table.put(puts)
        } finally {
            table.close()
        }
    }

    /**
     * 将单个 Product 写入 HBase 表
     * 表结构：rowKey=addName, 列族=info, 列=pro5115/pro4856/pro4767
     * @param name 表名
     * @param prod 商品聚合数据
     */
    def productPut(name: String, prod: Product): Unit = {
        val table: Table = conn.getTable(TableName.valueOf(name))
        try {
            val cf: Array[Byte] = Bytes.toBytes("info")
            val put: Put = new Put(Bytes.toBytes(prod.addName))
            put.addColumn(cf, Bytes.toBytes("pro5115"), Bytes.toBytes(prod.pro5115))
            put.addColumn(cf, Bytes.toBytes("pro4856"), Bytes.toBytes(prod.pro4856))
            put.addColumn(cf, Bytes.toBytes("pro4767"), Bytes.toBytes(prod.pro4767))
            table.put(put)
        } finally {
            table.close()
        }
    }

    /**
     * 将单个 conBean 写入 HBase 表
     * 表结构：rowKey=jump, 列族=info, 列=conversion
     * @param name 表名
     * @param bean 转化率数据
     */
    def convBeanPut(name: String, bean: conBean): Unit = {
        val table: Table = conn.getTable(TableName.valueOf(name))
        try {
            val cf: Array[Byte] = Bytes.toBytes("info")
            val put: Put = new Put(Bytes.toBytes(bean.jump))
            put.addColumn(cf, Bytes.toBytes("conversion"), Bytes.toBytes(bean.conv))
            table.put(put)
        } finally {
            table.close()
        }
    }

    /**
     * 根据 addName 查询 HBase 中的 Product 数据
     * 表结构：rowKey=addName, 列族=info, 列=pro5115/pro4856/pro4767
     * @param name 表名
     * @param addName 地址名（rowKey）
     * @return 查询到的 Product 对象，若不存在或数据不完整则返回 None
     */
    def productGet(name: String, addName: String): Option[Product] = {
        val table: Table = conn.getTable(TableName.valueOf(name))
        try {
            val get: Get = new Get(Bytes.toBytes(addName))
            get.addFamily(Bytes.toBytes("info"))
            val result: Result = table.get(get)

            if (result.isEmpty) {
                None
            } else {
                val cf: Array[Byte] = Bytes.toBytes("info")
                val pro5115Bytes: Array[Byte] = result.getValue(cf, Bytes.toBytes("pro5115"))
                val pro4856Bytes: Array[Byte] = result.getValue(cf, Bytes.toBytes("pro4856"))
                val pro4767Bytes: Array[Byte] = result.getValue(cf, Bytes.toBytes("pro4767"))

                if (pro5115Bytes == null || pro4856Bytes == null || pro4767Bytes == null) {
                    None
                } else {
                    Some(Product(addName, Bytes.toLong(pro5115Bytes), Bytes.toLong(pro4856Bytes), Bytes.toLong(pro4767Bytes)))
                }
            }
        } finally {
            table.close()
        }
    }

    /**
     * 根据 category_id 查询 HBase 中的聚合结果
     * @param categoryId 品类 ID
     * @return 查询到的 Info 对象，若不存在或数据不完整则返回 None
     */
    def infoGet(categoryId: String): Option[Info] = {
        val table: Table = conn.getTable(TableName.valueOf("categoryCount"))
        try {
            val get: Get = new Get(Bytes.toBytes(categoryId))
            get.addFamily(Bytes.toBytes("info"))
            val result: Result = table.get(get)

            if (result.isEmpty) {
                None
            } else {
                val cf: Array[Byte] = Bytes.toBytes("info")
                val viewBytes: Array[Byte] = result.getValue(cf, Bytes.toBytes("view"))
                val cartBytes: Array[Byte] = result.getValue(cf, Bytes.toBytes("cart"))
                val purchaseBytes: Array[Byte] = result.getValue(cf, Bytes.toBytes("purchase"))

                // 任一列为 null 视为数据不完整
                if (viewBytes == null || cartBytes == null || purchaseBytes == null) {
                    None
                } else {
                    Some(Info(categoryId, Bytes.toLong(viewBytes), Bytes.toLong(cartBytes), Bytes.toLong(purchaseBytes)))
                }
            }
        } finally {
            table.close()
        }
    }

    def close(): Unit = {
        if (conn != null) {
            conn.close()
        }
    }
}
