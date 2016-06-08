package top.oahnus.dao;

import org.apache.ibatis.session.SqlSession;
import top.oahnus.Bean.Message;
import top.oahnus.db.DBAccess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackstrom on 2016/5/22.
 */

//和message表的数据库交互
public class MessageDAO {

    //添加数据 单条数据
    public void addData(List<String> paramList){
        DBAccess dbAccess = new DBAccess();

        SqlSession sqlSession = null;

        try {
            sqlSession = dbAccess.getSqlSession();
            //通过sqlsession增加单条记录
            sqlSession.insert("Message.insertData",paramList);
            //mybatis的增删改是默认不提交事务的，需要手动提交
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //查找数据，按指定参数将数据分类返回
    public List<Message> queryMessageList(String command, String description){
        DBAccess dbAccess = new DBAccess();
        List<Message> messages = new ArrayList<>();
        SqlSession sqlSession = null;
        try {
            sqlSession = dbAccess.getSqlSession();
            //将参数command，description封装到一个Message中，传入selectList
            Message message = new Message();
            message.setCommand(command);
            message.setDescription(description);
            //通过sqlSession执行SQL语句,传入message
            messages = sqlSession.selectList("Message.queryMessageList",message);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
        return messages;
    }

    //按照ID值删除指定的单一数据
    public void deleteOne(int id){
        //创建直接访问数据库的类实例
        DBAccess dbAccess = new DBAccess();
        //创建SqlSession
        SqlSession sqlSession = null;

        try {
            sqlSession = dbAccess.getSqlSession();
            //通过sqlsession删除单条记录
            sqlSession.delete("Message.deleteOne",id);
            //mybatis的增删改是默认不提交事务的，需要手动提交
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //按照ID值删除指定的单一数据
    public void deleteBatch(List<Integer> ids){
        //创建直接访问数据库的类实例
        DBAccess dbAccess = new DBAccess();
        //创建SqlSession
        SqlSession sqlSession = null;

        try {
            sqlSession = dbAccess.getSqlSession();
            //通过sqlsession删除多条记录
            sqlSession.delete("Message.deleteBatch",ids);
            //mybatis的增删改是默认不提交事务的，需要手动提交
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //按照command的值修改单条数据
    public void updateOne(Message message){
        DBAccess dbAccess = new DBAccess();
        SqlSession sqlSession = null;

        try{
            sqlSession = dbAccess.getSqlSession();
            //通过sqlsession修改数据
            sqlSession.update("Message.updateData", message);
            //提交数据
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        MessageDAO messageDAO = new MessageDAO();
        messageDAO.queryMessageList("新闻","");
    }


//    /*JDBC方式连接数据库*/
//    public List<Message> queryMessageList(String command, String description){
//        //设置一个list存放参数
//        List<String> paramList = new ArrayList<>();
//        //
//        List<Message> messages = new ArrayList<>();
//
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/micro_message","root","chuxuan123sh");
//
//            StringBuilder sql = new StringBuilder(
//                    "SELECT ID,COMMAND,DESCRIPTION,CONTENT FROM message WHERE 1=1"
//            );
//
//            //添加查询条件
//            if(command!=null&&(!command.trim().equals(""))){
//                sql.append(" AND COMMAND=?");
//                paramList.add(command);
//            }
//            //添加查询条件,模糊搜索
//            if(description!=null&&(!description.trim().equals(""))){
//                sql.append(" AND DESCRIPTION LIKE '%' ? '%'");
//                paramList.add(description);
//            }
//
//            PreparedStatement statement =  conn.prepareStatement(sql.toString());
//
//            //将参数添加进sql中，代替？号
//            for(int i=0;i<paramList.size();i++){
//                //sql参数从0开始计数
//                statement.setString(i+1,paramList.get(i));
//            }
//
//            ResultSet resultSet = statement.executeQuery();
//
//            //将查询结果保存到bean中
//            while(resultSet.next()){
//                Message message = new Message();
//                messages.add(message);
//                message.setId(resultSet.getInt("ID"));
//                message.setCommand(resultSet.getString("COMMAND"));
//                message.setDescription(resultSet.getString("DESCRIPTION"));
//                message.setContent(resultSet.getString("CONTENT"));
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return messages;
//    }
}