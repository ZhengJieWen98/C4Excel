package csdn;

import csdn.pojo.StaffSalary;
import csdn.util.ExcelUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException {

        Map<Integer, StaffSalary> map = new TreeMap<>();
        InputStream resourceAsStream1 = ExcelUtil.class.getClassLoader().getResourceAsStream("市场部-薪酬表.xlsx");
        InputStream resourceAsStream2 = ExcelUtil.class.getClassLoader().getResourceAsStream("大客户部-薪酬表.xlsx");
        InputStream resourceAsStream3 = ExcelUtil.class.getClassLoader().getResourceAsStream("研发部-薪酬表.xlsx");
        InputStream resourceAsStream4 = ExcelUtil.class.getClassLoader().getResourceAsStream("销售部-薪酬表.xlsx");
        ExcelUtil.readExcel_remuneration("市场部",resourceAsStream1,map);
        ExcelUtil.readExcel_remuneration("大客户部",resourceAsStream2,map);
        ExcelUtil.readExcel_remuneration("研发部",resourceAsStream3,map);
        ExcelUtil.readExcel_remuneration("销售部",resourceAsStream4,map);

        InputStream resourceAsStream5 = ExcelUtil.class.getClassLoader().getResourceAsStream("员工五险一金申报表.xlsx");
        ExcelUtil.readExcel_WXYJ(resourceAsStream5,map);
        Set<Integer> keys = map.keySet();
        for (Integer key:keys){
            StaffSalary staffSalary = map.get(key);
            System.out.println(staffSalary);
        }

        ExcelUtil.fillExcel_Department(map,new File("企业人员月度工资成本支付表test.xlsx"));
    }
}
