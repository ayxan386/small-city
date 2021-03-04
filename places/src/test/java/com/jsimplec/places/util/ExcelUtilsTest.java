package com.jsimplec.places.util;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ExcelUtilsTest {

  public static final int FIRST_SHEET = 0;
  @Spy
  MockExcelExportable mockExcelExportable = new MockExcelExportable();
  @InjectMocks
  private ExcelUtils excelUtils;

  @BeforeEach
  void setUp() {
  }

  @Test
  void exportToExcel() {
    Workbook workbook = excelUtils.exportToExcel(mockExcelExportable);

    assertThat(workbook).isNotNull();
    assertThat(workbook.getSheetName(FIRST_SHEET)).isEqualTo(mockExcelExportable.getSheetName());
    assertThat(ReflectionTestUtils.getField(excelUtils, "commonStyle")).isNotNull();
    assertThat(ReflectionTestUtils.getField(excelUtils, "emptyCellStyle")).isNotNull();
  }

  private static class MockExcelExportable implements TableDataExporter {

    @Override
    public List<String> getColumnOrder() {
      return List.of("a", "b", "c", "d");
    }

    @Override
    public String getSheetName() {
      return "alphabet";
    }

    @Override
    public List<Map<String, Object>> getData() {
      return List.of(
          Map.of("a", 1, "b", 2),
          Map.of("c", 3, "d", 2)
      );
    }
  }
}