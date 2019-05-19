package com.cqut.icode.dao.file;

import com.cqut.icode.entities.Teacher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.util.List;

/**
 * @author 谭强
 * @date 2019/5/18
 */
public class EntityDao {
    public static void main(String[] args) {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(
                    new File("src/main/resources/falseData.json"))
            );

            StringBuilder jsonStr = new StringBuilder();
            String temp = "";

            while ((temp = bf.readLine()) != null) {
                jsonStr.append(temp);
            }

            bf.close();

            System.out.println(jsonStr);

//            JSONObject jsonObject = JSONObject.fromObject(jsonStr.toString());
//            System.out.println("\n\n");
//            System.out.println(jsonObject);

            JSONArray jsonArray = JSONArray.fromObject(jsonStr.toString());
            System.out.println("\n\n");
            System.out.println(jsonArray);

            List<Teacher> list = (List) JSONArray.toCollection(jsonArray, Teacher.class);
            System.out.println(list.get(0).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
