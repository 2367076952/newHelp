package com.nayuniot.modules.management.service.impl;


import com.nayuniot.exception.EntityExistException;
import com.nayuniot.modules.approval.domain.Approval;
import com.nayuniot.modules.approval.repository.ApprovalRepository;
import com.nayuniot.modules.management.domain.Employee;
import com.nayuniot.modules.management.repository.DistrictRepository;
import com.nayuniot.modules.management.repository.EmployeeRepository;
import com.nayuniot.modules.management.repository.NationRepository;
import com.nayuniot.modules.management.service.EmployeeService;
import com.nayuniot.modules.management.service.dto.EmployeeDto;
import com.nayuniot.modules.management.service.dto.EmployeeQueryCriteria;
import com.nayuniot.modules.management.service.mapstruct.EmployeeMapper;
import com.nayuniot.modules.money.domain.Purpose;
import com.nayuniot.modules.money.repository.PurposeRepository;
import com.nayuniot.modules.system.domain.Dept;
import com.nayuniot.modules.system.repository.DeptRepository;
import com.nayuniot.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    //        身份证号的正则表达式
    final static String ID_NUMBER = "^[1-9][0-9]{5}(18|19|20)[0-9]{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)[0-9]{3}([0-9]|(X|x))";


    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;
    private final ApprovalRepository employeeApprovalRepository;
    private final PurposeRepository purposeRepository;
    private final DistrictRepository districtRepository;
    private final DeptRepository deptRepository;
    private final NationRepository nationRepository;

    /**
     * 分页条件查询
     *
     * @param employee 条件
     * @param pageable 分页参数
     * @return
     */
    @Override
    public Map<String, Object> queryAll(EmployeeQueryCriteria employee, Pageable pageable) {
        Page<Employee> page = employeeRepository.findAll((root, criteriaQuery, criteriaBuilder) ->
                QueryHelp.getPredicate(root, employee, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(employeeMapper::toDto).getContent(), page.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        employeeRepository.deleteAllByIdIn(ids);
    }

    //新增人员
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Employee employee) {
        if (employeeRepository.findAllByIdNumberEquals(employee.getIdNumber()) != null) {
            throw new EntityExistException(Employee.class, "身份证号已存在", employee.getIdNumber());
        }
        if (employeeRepository.findByPhone(employee.getPhone()) != null) {
            throw new EntityExistException(Employee.class, "手机号重复", employee.getPhone());
        }
        employeeRepository.save(employee);
    }

    //修改人员信息
    @Override
    public void update(Employee employee) {
        Employee resource = employeeRepository.findById(employee.getId()).orElseGet(Employee::new);
        ValidationUtil.isNull(employee.getId(), "employee", "id", employee.getId());
        employee.setId(resource.getId());
        employeeRepository.save(employee);
    }

    /**
     * 修改申请
     *
     * @param employeeApproval
     */
    @Override
    public void updateApproval(Approval employeeApproval) {
        //通过urlAddress查到相同的请求路径
        List<Approval> allByUrlAddressEquals = employeeApprovalRepository.findAllByUrlAddressEquals(employeeApproval.getUrlAddress());
        //遍历 进行id的比较和请求方式都一样
        for (Approval allByUrlAddressEqual : allByUrlAddressEquals) {
            //再去查看当条记录的状态 为0说明已经发起了请求 不能再次发起
            if (allByUrlAddressEqual.getRowId().equals(employeeApproval.getRowId()) &&
                    allByUrlAddressEqual.getStatus() == 0) {
                throw new EntityExistException(Approval.class, "已被修改", employeeApproval.getRowId().toString());
            }
        }
        employeeApprovalRepository.save(employeeApproval);
    }


    /**
     * 导出数据
     *
     * @param criteria 条件
     * @return
     */
    @Override
    public List<EmployeeDto> queryAll(EmployeeQueryCriteria criteria) {
        List<Employee> list = employeeRepository
                .findAll((root, criteriaQuery, criteriaBuilder)
                        -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
        return employeeMapper.toDto(list);
    }


    /**
     * 导出数据
     *
     * @param employeeDtos
     * @param response     /
     * @throws IOException
     */
    @Override
    public void download(List<EmployeeDto> employeeDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        StringBuilder s = new StringBuilder();

        for (EmployeeDto employeeDto : employeeDtos) {
            s.setLength(0);
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("序号", employeeDto.getId());
            map.put("姓名", employeeDto.getName());
            map.put("联系电话", employeeDto.getPhone());
            map.put("身份证号", employeeDto.getIdNumber());
            map.put("性别", employeeDto.getGender());
            map.put("年龄", employeeDto.getAge());
            map.put("民族", employeeDto.getNation().getNation());
            map.put("家庭住址", employeeDto.getAddress().getName() + employeeDto.getDetailed());
            map.put("户籍所在地", employeeDto.getProvinceId().getName() + employeeDto.getCityId().getName() + employeeDto.getRegister().getName());
            for (Purpose purpose : employeeDto.getPurpose()) {
                s.append(purpose.getName());
            }
            map.put("救助原因", s.toString());
            map.put("备注", employeeDto.getRemarks());

            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }


    @Override
    public List<Employee> findByIdNotIn(Set<Long> id) {
        return employeeRepository.findByIdNotIn(id);
    }

    @Override
    public String uploadEmployee(String path) throws IOException {
//        存储有误的信息的位置
        StringBuilder errorEmployee = new StringBuilder();
//        身份证号的正则表达式
        final String ID_NUMBER = "^[1-9][0-9]{5}(18|19|20)[0-9]{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)[0-9]{3}([0-9]|(X|x))";
//        数据集合
        List<Map<Employee, Integer>> employees = new LinkedList<>();
//        身份证号解析工具类
        idCardUtil util = new idCardUtil();
//        每一行数据存储
        Employee employee = new Employee();
//        判断列的位置
        Map<String, Integer> map = new HashMap<>();
        //        获取工作簿
        XSSFWorkbook sheets;
//      表格路径
        sheets = new XSSFWorkbook(path);
        XSSFSheet sheetAt = sheets.getSheetAt(0);
        // 2、获取工作簿中的表,对表中的设置，通过下标去拿第一张表
        // 3、getLastRowNum()方法返回的是最后一行的索引，会比总行数少1
        int lastRowNum = sheetAt.getLastRowNum() + 1;
//       获取行
        for (int i = 0; i <= lastRowNum; i++) {
            // 获取工作表的数据
            XSSFRow row = sheetAt.getRow(i);
//            获取单元格中的内容
            if (row == null)
                continue;
            for (Cell cell : row) {
//              类型强转为string
                cell.setCellType(CellType.STRING);
//              单元格里面的数据
                String stringCellValue = cell.getStringCellValue();
//                本次单元格数据要是为空 则进入下一个单元格
                if (stringCellValue == null || stringCellValue.equals(""))
                    continue;
//                判断map中的值是否为空 如果为空才查找所需要的列的位置
                if (map.get("name") == null ||
                        map.get("gender") == null ||
                        map.get("phone") == null ||
                        map.get("address") == null ||
                        map.get("detail") == null ||
                        map.get("purpose") == null ||
                        map.get("nation") == null) {
                    if (stringCellValue.contains("姓名")) {
                        map.put("name", cell.getColumnIndex());
                        continue;
                    }
                    if (stringCellValue.contains("身份证")) {
                        map.put("idNumber", cell.getColumnIndex());
                        continue;
                    }
                    if (stringCellValue.contains("电话")) {
                        map.put("phone", cell.getColumnIndex());
                        continue;
                    }
                    if (stringCellValue.contains("小区")) {
                        map.put("address", cell.getColumnIndex());
                        continue;
                    }
                    if (stringCellValue.contains("救助")) {
                        map.put("purpose", cell.getColumnIndex());
                        continue;
                    }
                    if (stringCellValue.contains("住址") || stringCellValue.contains("地址")) {
                        map.put("detail", cell.getColumnIndex());
                        continue;
                    }
                    if (stringCellValue.contains("民族")) {
                        map.put("nation", cell.getColumnIndex());
                        continue;
                    }
                }
//          确认了位置之后进行填充数据
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    if (entry.getValue() == (cell.getColumnIndex())) {
                        switch (entry.getKey()) {
//                            姓名
                            case "name":
                                employee.setName(stringCellValue);
                                break;
//                                手机号
                            case "phone":
                                employee.setPhone(stringCellValue);
                                break;
//                               身份证号
                            case "idNumber":
                                if (stringCellValue.matches(ID_NUMBER)) {
                                    employee.setIdNumber(stringCellValue);
                                    try {
//                                  年龄
                                        employee.setAge(util.getAgeByIdCard(stringCellValue));
                                        //                                性别
                                        employee.setGender(util.getGenderByIdCard(stringCellValue));
//                                    户籍省
                                        employee.setProvinceId(districtRepository.findByCodeEquals((stringCellValue.substring(0, 2) + "0000")));
//                                    户籍城市
                                        employee.setCityId(districtRepository.findByCodeEquals(stringCellValue.substring(0, 4) + "00"));
//                                  户籍区县
                                        employee.setRegister(districtRepository.findByCodeEquals(stringCellValue.substring(0, 6)));
                                    } catch (Exception e) {
                                        continue;
//
                                    }
                                } else {
                                    continue;
                                }
                                break;
//                                住址
                            case "address":
                                Dept dept = deptRepository.findFirstByNameLike(stringCellValue);
                                employee.setAddress(dept);
                                break;
//                                详细地址
                            case "detail":
                                employee.setDetailed(stringCellValue);
                                break;
//                                民族
                            case "nation":
                                employee.setNation(nationRepository.findFirstByNationLike('%' + stringCellValue + '%'));
                                break;
//                                救助原因
                            case "purpose":
                                StringBuilder purposeList = new StringBuilder();
                                Set<Purpose> purposes = new HashSet<>();
                                String[] split = stringCellValue.split(",");
                                if (split.length <= 1) {
                                    split = stringCellValue.split("，");
                                }
                                if (split.length <= 1) {
                                    split = stringCellValue.split(" ");
                                }
                                for (int s = 0; s <= split.length - 1; s++) {
                                    Purpose allByNameEquals = purposeRepository.findAllByNameEquals(split[s]);
                                    if (allByNameEquals != null) {
                                        purposeList.append(allByNameEquals.getId()).append(",");
                                        purposes.add(allByNameEquals);
                                    }
                                }

                                employee.setPurposeList(purposeList.toString());
                                employee.setPurpose(purposes);
                        }
//
                    }
                }

            }

            Map<Employee, Integer> maps = new HashMap();
            maps.put(employee, row.getRowNum());
            employees.add(maps);
            employee = new Employee();
        }


        //    释放资源
        sheets.close();
        for (Map<Employee, Integer> employeeIntegerMap : employees) {
            for (Employee key : employeeIntegerMap.keySet()) {
                if (key.getName() != null &&
                        key.getIdNumber() != null &&
                        key.getGender() != null &&
                        key.getPurposeList() != null &&
                        key.getPhone() != null &&
                        key.getPurpose() != null &&
                        key.getAddress() != null &&
                        key.getDetailed() != null &&
                        key.getProvinceId() != null &&
                        key.getCityId() != null &&
                        key.getRegister() != null) {
                    try {
                        Employee allByIdNumberEquals = employeeRepository.findAllByIdNumberEquals(key.getIdNumber());
                        if (allByIdNumberEquals != null) {
                            String s = "";
                            Set<Purpose> set = new HashSet<>(allByIdNumberEquals.getPurpose());
                            for (Purpose purpose : key.getPurpose()) {
                                if (!allByIdNumberEquals.getPurposeList().contains(purpose.getId().toString())) {
                                    set.add(purpose);
                                    s = allByIdNumberEquals.getPurposeList() + purpose.getId() + ",";
                                }
                            }
                            allByIdNumberEquals.setPurpose(set);
                            allByIdNumberEquals.setPurposeList(s);
                            update(allByIdNumberEquals);
                        } else {
                            save(key);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        errorEmployee.append(employeeIntegerMap.get(key)).append(",");
                    }
                } else {
                    errorEmployee.append(employeeIntegerMap.get(key)).append(",");
                }

            }
        }
        return errorEmployee.toString();
    }

    @Override
    public void errorEmployeeDownload(String path, String errorList, HttpServletResponse response) throws IOException {
        List<List<Object>> listExcel = new LinkedList<>();
        //        获取工作簿
        XSSFWorkbook sheets;
//      表格路径
        sheets = new XSSFWorkbook(path);
        XSSFSheet sheetAt = sheets.getSheetAt(0);
        // 2、获取工作簿中的表,对表中的设置，通过下标去拿第一张表
        // 3、getLastRowNum()方法返回的是最后一行的索引，会比总行数少1
        int lastRowNum = sheetAt.getLastRowNum() + 1;
//       获取行
        for (int i = 0; i <= lastRowNum; i++) {
            if (errorList.contains(i + "")) {
                // 获取工作表的数据
                XSSFRow row = sheetAt.getRow(i);
//            获取单元格中的内容
                if (row == null)
                    continue;
                List<Object> list = new LinkedList<>();
                for (Cell cell : row) {
//              类型强转为string
                    cell.setCellType(CellType.STRING);
//              单元格里面的数据
                    String stringCellValue = cell.getStringCellValue();
                    list.add(stringCellValue);
                }
                listExcel.add(list);
            }
        }
        FileUtil.downloadExcelList(listExcel, response);

    }


}
