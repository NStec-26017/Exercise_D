package model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.dto.Department;
import model.exception.ServiceException;
import util.TestUtil;

@DisplayName("UC02【部門一覧表示】機能のテスト")
public class GetDepartmentListServiceTest {

    GetDepartmentListService target;

    @BeforeEach
    public void setUp() throws Exception {
        TestUtil.initDB();
        target = new GetDepartmentListService();
    }

    @Test
    @DisplayName("部門名を複数取得:データあり")
    public void testGetDeptName01() throws Exception {
        TestUtil.setDS101ToDB();
        List<Department> expected = TestUtil.getDS101();
        List<Department> actual = target.readDepartmentAll();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("部門名を複数取得:データなし")
    public void testGetDeptName02() throws Exception {
        TestUtil.setDS102ToDB();
        List<Department> expected = TestUtil.getDS102();
        List<Department> actual = target.readDepartmentAll();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("部門名を複数取得:例外処理（取得失敗）")
    public void testGethDeptName03() throws Exception {
        TestUtil.clearDB();
        assertThrows(ServiceException.class, () -> target.readDepartmentAll());
    }

    @Test
    @DisplayName("部門名を複数取得:例外処理（DB処理エラー）")
    public void testGetDeptName04() throws Exception {
        TestUtil.changeDBSetting();
        try {
            assertThrows(ServiceException.class, () -> target.readDepartmentAll());
        } finally {
            TestUtil.resetDBSetting();
        }
    }

}
