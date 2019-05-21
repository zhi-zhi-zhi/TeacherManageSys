package com.cqut.icode.dao.fieDao;

import com.cqut.icode.entities.Teacher;
import net.sf.json.JSONArray;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            String temp;

            while ((temp = bf.readLine()) != null) {
                jsonStr.append(temp);
            }

            bf.close();

            System.out.println(jsonStr);

//            JSONObject jsonObject = JSONObject.fromObject(jsonStr.toString());
//            System.out.println("\n\n");
//            System.out.println(jsonObject);



//            JSONArray jsonArray = JSONArray.fromObject(jsonStr.toString());
            System.out.println("\n\n");
//            System.out.println(jsonArray);

//            List<Teacher> list = (List) JSONArray.toCollection(jsonArray, Teacher.class);

            System.out.println();
            List<Teacher> teachers = new ArrayList<>();
            teachers.add(new Teacher((long)1, (long)1, "1",
                    "1", 18, "1", "1", (float)10));
            teachers.add(new Teacher((long)1, (long)1, "1",
                    "1", 18, "1", "1", (float)10));


            List<Map<String, Object>> mapListTeachers = new ArrayList<>();
            Map<String, Object> map;

            for (Teacher teacher : teachers) {
                map = new HashMap<>(8);
                map.put("id", teacher.getId());
                map.put("tno", teacher.getTno());
                map.put("name", teacher.getName());
                map.put("gender", teacher.getGender());
                map.put("age", teacher.getAge());
                map.put("academy", teacher.getAcademy());
                map.put("dept", teacher.getDept());
                map.put("salary", teacher.getSalary());
                mapListTeachers.add(map);
            }


            System.out.println(mapListTeachers);
            System.out.println(JSONArray.fromObject(teachers));
            System.out.println(JSONArray.fromObject(mapListTeachers));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
