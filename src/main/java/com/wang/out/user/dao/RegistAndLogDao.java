package com.wang.out.user.dao;


import com.alibaba.druid.pool.DruidDataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.jdbc.PreparedStatement;
import com.wang.out.common.BaseDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wang.out.common.ResultPojo;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowire;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import javax.management.Query;
import javax.xml.transform.Result;


@Repository("registAndLogDao")
public class RegistAndLogDao extends BaseDao {

    /*
    * 验证用户名和密码还有注册状态
    * */
    public Map<String, String> checklogDao(String username, String password) throws SQLException {
        conn = getConnection();

        String sqlSearch = "select * from t_user where username=? and password=?";
        preparedStatement = conn.prepareStatement(sqlSearch);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet set = preparedStatement.executeQuery();
        Map<String, String> map = new HashMap<String, String>();
        String username1 = "";
        while (set.next()) {
            map.put("username", set.getString("username"));
            map.put("password", set.getString("password"));
            map.put("isregister", set.getString("isregister"));
        }

        conn.close();

        return map;
    }

    /*
    * 登录后生成token写入Token和时间
    * */
    public void createTokenDao(String username, String token, Long time) throws SQLException {
        conn = getConnection();

        String sqlSearch = "select * from t_logcheck where username=?";
        preparedStatement = conn.prepareStatement(sqlSearch);
        preparedStatement.setString(1, username);
        ResultSet set = preparedStatement.executeQuery();
        String username1 = "";
        while (set.next()) {
            username1 = set.getString("username");
        }

        if (username1.equals("")) {

            String sqlInsert = "INSERT INTO t_logcheck (username,token,data) VALUES (?,?,?);";
            preparedStatement = conn.prepareStatement(sqlInsert);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, token);
            preparedStatement.setLong(3, time);
            preparedStatement.execute();
            conn.close();
        } else {

            String sqlUpdate = "UPDATE t_logcheck SET token=?,data=? WHERE username=?;";
            preparedStatement = conn.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, token);
            preparedStatement.setLong(2, time);
            preparedStatement.setString(3, username);
            preparedStatement.execute();
            conn.close();
        }


    }


    /*
    * 判断checked是正确，并修改注册状态
    * 返回错误，验证码不正确或者是已经注册过了
    * */
    public boolean checkedDao(String checked) throws SQLException {

        conn = getConnection();

        String sqlSearch = "select * from t_check where checked=?";
        preparedStatement = conn.prepareStatement(sqlSearch);
        preparedStatement.setString(1, checked);
        ResultSet set = preparedStatement.executeQuery();
        String username = "";
        while (set.next()) {
            username = set.getString("username");
        }

        if (username.equals("")) {
            conn.close();
            return false;
        }

        String sqlSearch1 = "select * from t_user where username=?";
        preparedStatement = conn.prepareStatement(sqlSearch1);
        preparedStatement.setString(1, username);
        ResultSet set1 = preparedStatement.executeQuery();
        String username1 = "";
        String isregister = "";
        while (set1.next()) {
            username1 = set1.getString("username");
            isregister = set1.getString("isregister");
        }

        if (username1.equals("") || isregister.equals("1")) {
            conn.close();
            return false;
        }


        if (!StringUtils.isBlank(username)) {
            String sqlUpdate = "update t_user set isregister ='1' where username=?";
            preparedStatement = conn.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, username);
            preparedStatement.execute();
            String sqlDelete = "DELETE FROM t_check WHERE username=?";
            preparedStatement = conn.prepareStatement(sqlDelete);
            preparedStatement.setString(1, username);
            preparedStatement.execute();
            conn.close();
            return true;
        }
        conn.close();
        return false;
    }

    /*
    * 判断用户是否存在
    * */
    public boolean usernameIsExistDao(String username) throws SQLException {

        conn = getConnection();

        String sqlSearch = "select * from t_user where username=?";
        preparedStatement = conn.prepareStatement(sqlSearch);
        preparedStatement.setString(1, username);
        ResultSet set = preparedStatement.executeQuery();
        String username1 = "";
        String isregister = "";
        while (set.next()) {
            username1 = set.getString("username");
            isregister = set.getString("isregister");
        }

        conn.close();


        if (username.equals(username1) && isregister.equals("1")) {
            return true;
        } else {
            return false;
        }


    }


    /*
    * 注册新用户
    *
    * */
    public void userRegistDao(Map<String, String[]> usermap, Map<String, String[]> checkmap) throws SQLException {

        conn = getConnection();

        /*
        * 删除原来未激活的用户
        * 删除原来未激活的UUID
        * */
        String sqlSearch = "select * from t_user where username=?";
        preparedStatement = conn.prepareStatement(sqlSearch);
        preparedStatement.setString(1, usermap.get("username")[0]);
        ResultSet set = preparedStatement.executeQuery();

        String isregister = "";
        String username = "";

        while (set.next()) {
            username = set.getString("username");
            isregister = set.getString("isregister");
        }

        if (username.equals(usermap.get("username")[0]) && isregister.equals("0")) {
            String sqlDelete1 = "DELETE FROM t_check WHERE username =?;";
            String sqlDelete2 = "DELETE FROM t_user WHERE username =?;";
            preparedStatement = conn.prepareStatement(sqlDelete1);
            preparedStatement.setString(1, usermap.get("username")[0]);
            preparedStatement.execute();
            preparedStatement = conn.prepareStatement(sqlDelete2);
            preparedStatement.setString(1, usermap.get("username")[0]);
            preparedStatement.execute();
        }

        /*
        * 注册新用户
        * */
        String sql1 = "";
        int lenth = usermap.size();
        List<String> valueList = new ArrayList<>();
        for (String key : usermap.keySet()) {
            valueList.add(usermap.get(key)[0]);
            sql1 = sql1 + "?";
            if (lenth == 1) {
                continue;
            } else {
                sql1 = sql1 + ",";
                lenth--;
            }
        }

        String sqlRegist1 = "insert into t_user (password,id,isregister,username) values (" + sql1 + ")";
        preparedStatement = conn.prepareStatement(sqlRegist1);

        for (int a = 0; a < valueList.size(); a++) {
            preparedStatement.setString(a + 1, valueList.get(a));
        }
        preparedStatement.execute();


        String sqlRegist2 = "insert into t_check (username,checked) values (?,?)";
        preparedStatement = conn.prepareStatement(sqlRegist2);
        preparedStatement.setString(1, checkmap.get("username")[0]);
        preparedStatement.setString(2, checkmap.get("checked")[0]);
        preparedStatement.execute();

        conn.close();

    }


}

