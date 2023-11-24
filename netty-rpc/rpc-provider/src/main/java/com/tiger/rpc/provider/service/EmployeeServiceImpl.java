package com.tiger.rpc.provider.service;

import com.tiger.rpc.service.Employee;
import com.tiger.rpc.service.EmployeeService;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: EmployeeServiceImpl
 * @Author: Zeng.h
 * @Date: 2023/11/24
 * @Description:
 * @Version: 1.0
 **/
public class EmployeeServiceImpl implements EmployeeService {
    private static ConcurrentHashMap<Integer, Employee> map = new ConcurrentHashMap<>();

    @Override
    public void add(Employee employee) {
        map.put(employee.getId(), employee);

    }

    @Override
    public Employee get(int id) {
        return map.get(id);
    }
}
