package com.cqut.icode.dao.otherDao.file;

import com.cqut.icode.dao.otherDao.TeacherDao;
import com.cqut.icode.entities.Teacher;
import net.sf.json.JSONArray;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author 谭强
 * @date 2019/5/18
 */
public class TeacherFileDao implements TeacherDao {
    private static List<Teacher> teacherList = null;
    private static List<Teacher> teacherListBackup = null;

    @Override
    public List<Teacher> listTeachers() {
        if (teacherList == null) {
            getTeacherList();
        }

        return teacherList;
    }

    @Override
    public boolean saveTeacher(Teacher teacher) {
        if (teacherList == null) {
            return false;
        }

        backup();

        // 数据库的id字段是自增，文件没得自增
        // so
        teacher.setId(teacher.getTno());
        teacherList.add(teacher);

        return writeToFile();
    }

    @Override
    public boolean updateTeacher(Teacher teacher) {
        if (teacherList == null) {
            return false;
        }

        backup();

        for (Teacher teacher1 : teacherList) {
            if (teacher.getId().equals(teacher1.getId())) {
                teacher1.setName(teacher.getName());
                teacher1.setGender(teacher.getGender());
                teacher1.setAge(teacher.getAge());
                teacher1.setAcademy(teacher.getAcademy());
                teacher1.setDept(teacher.getDept());
                teacher1.setSalary(teacher.getSalary());
            }
        }

        return writeToFile();
    }

    @Override
    public boolean removeTeachers(List<Long> ids) {
        if (teacherList == null) {
            return false;
        }

        List<Integer> indexes = new ArrayList<>();

        // 获取要删除的对象在teacherList中的下标index
        for (int i = 0; i < teacherList.size(); i++) {
            for (Long id : ids) {
                if (teacherList.get(i).getId().equals(id)) {
                    indexes.add(i);
                }
            }
        }

        // 从较大的index开始删
        for (int i = indexes.size() - 1; i >= 0; i--) {
            teacherList.remove((int)indexes.get(i));
        }

        return writeToFile();
    }


    /** 获取数据 */
    private void getTeacherList() {
        try {
//            Why used Objects.requireNonNull()
//            This inspection analyzes method control and data flow to report possible conditions that are always true or false, expressions whose value is statically proven to be constant, and situations that can lead to nullability contract violations.
//            Variables, method parameters and return values marked as @Nullable or @NotNull are treated as nullable (or not-null, respectively) and used during the analysis to check nullability contracts, e.g. report NullPointerException (NPE) errors that might be produced.
//                    More complex contracts can be defined using @Contract annotation, for example:
//            @Contract("_, null -> null") — method returns null if its second argument is null @Contract("_, null -> null; _, !null -> !null") — method returns null if its second argument is null and not-null otherwise @Contract("true -> fail") — a typical assertFalse method which throws an exception if true is passed to it
//            The inspection can be configured to use custom @Nullable
//                    @NotNull annotations (by default the ones from annotations.jar will be used)
            System.out.println(Objects.requireNonNull(TeacherFileDao.class.getClassLoader()
                    .getResource("falseData.json")).toString());

            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(
                    new File(Objects.requireNonNull(TeacherFileDao.class.getClassLoader()
                            .getResource("falseData.json")).getFile())),
                    StandardCharsets.UTF_8));

            StringBuilder jsonStr = new StringBuilder();
            String temp;

            while ((temp = bf.readLine()) != null) {
                jsonStr.append(temp);
            }

            bf.close();

            JSONArray jsonArray = JSONArray.fromObject(jsonStr.toString());
            System.out.println(jsonArray);
            teacherList = (List<Teacher>) JSONArray.toCollection(jsonArray, Teacher.class);
            System.out.println(teacherList);

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    /** 增删改前对数据备份 */
    private void backup() {
        if (teacherList == null) {
            return;
        }

        // 将原List转化为json再转化成List，实现深度复制
        teacherListBackup = (List<Teacher>) (JSONArray.toCollection(JSONArray.fromObject(teacherList)));
    }

    /** 增删改后保存至文件异常，恢复数据到修改前状态 */
    private void recover() {
        if (teacherListBackup == null) {
            return;
        }

        teacherList = teacherListBackup;
    }

    /**
     * @return 更新后的数据保存到文件成功为true
     */
    private boolean writeToFile() {
        try {
            // 改变的是项目打包后的target里面的falseData.json，不是src.main.resources里的falseData.json
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    new File(Objects.requireNonNull(TeacherFileDao.class.getClassLoader()
                            .getResource("falseData.json")).getFile())),
                    StandardCharsets.UTF_8));

            System.out.println(JSONArray.fromObject(teacherList).toString());
            bw.write(JSONArray.fromObject(teacherList).toString());
            bw.flush();
            bw.close();

            return true;
        } catch (IOException e) {
            recover();
            e.printStackTrace();
        }

        return false;
    }
}
