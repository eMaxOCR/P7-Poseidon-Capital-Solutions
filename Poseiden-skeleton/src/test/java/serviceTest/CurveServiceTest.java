package serviceTest;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurveServiceTest {

	@Mock
	CurvePointRepository curvePointRepository;
	
	@InjectMocks
	@Spy	//Test getRuleNameById without BDD.
	CurveService curveService;
	
	private CurvePoint curvePoint;
	private CurvePoint curvePoint2;
	private CurvePoint updatedCurvePoint;
	private List<CurvePoint> curvePointList = new ArrayList<>();
	
	@BeforeEach
	public void setupTest() {
		CurvePoint setupCurvePoint = new CurvePoint();
		setupCurvePoint.setId(1);
		setupCurvePoint.setCurveId(10);
		setupCurvePoint.setTerm(10.0);
		setupCurvePoint.setValue(15.0);
		setupCurvePoint.setCreationDate(LocalDateTime.now());
		curvePoint = setupCurvePoint;
		
		CurvePoint setupUpdatedCurvePoint = new CurvePoint();
		setupUpdatedCurvePoint.setId(1);
		setupUpdatedCurvePoint.setCurveId(12);
		setupUpdatedCurvePoint.setTerm(12.0);
		setupUpdatedCurvePoint.setValue(18.0);
		updatedCurvePoint = setupUpdatedCurvePoint;
		
		CurvePoint secondCurvePoint = new CurvePoint();
		secondCurvePoint.setId(2);
		secondCurvePoint.setCurveId(20);
		secondCurvePoint.setTerm(20.0);
		secondCurvePoint.setValue(25.0);
		curvePoint2 = secondCurvePoint;
		
		curvePointList.add(curvePoint);
		curvePointList.add(secondCurvePoint);
	}
	
	@Test
	public void saveTest() {
		// ARRANGE
		when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);
		
		// ACT
		CurvePoint savedCurvePoint = curveService.save(curvePoint);
		
		// ASSERT
		assertNotNull(savedCurvePoint);
		assertEquals(curvePoint.getTerm(), savedCurvePoint.getTerm());
		verify(curvePointRepository, times(1)).save(curvePoint);
	}
	
	@Test
	public void getCurvePointByIdTest() {
		// ARRANGE
		when(curvePointRepository.getReferenceById(any(Integer.class))).thenReturn(curvePoint);
		
		// ACT
		CurvePoint foundCurvePoint = curveService.getCurvePointById(curvePoint.getId());
		
		// ASSERT
		assertNotNull(foundCurvePoint);
		assertEquals(curvePoint.getId(), foundCurvePoint.getId());
	}
	
	@Test
	public void getAllCurvePointTest() {
		// ARRANGE
		when(curvePointRepository.findAll()).thenReturn(curvePointList);
		
		// ACT
		List<CurvePoint> curvePoints = curveService.getAllCurvePoint();
		
		// ASSERT
		assertNotNull(curvePoints);
		assertEquals(2, curvePoints.size());
		assertEquals(curvePoint.getId(), curvePoints.get(0).getId());
		assertEquals(curvePoint2.getId(), curvePoints.get(1).getId());
	}
	
	@Test
	public void validateTest() {
		// ARRANGE
		when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);
		
		// ACT
		CurvePoint validatedCurvePoint = curveService.validate(curvePoint);
		
		// ASSERT
		assertNotNull(validatedCurvePoint);
		assertNotNull(validatedCurvePoint.getCreationDate());
		verify(curvePointRepository, times(1)).save(curvePoint);
	}
	
	@Test
	public void updateTest() {
		// ARRANGE
		when(curvePointRepository.getReferenceById(any(Integer.class))).thenReturn(curvePoint);
		when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(updatedCurvePoint);
		
		// ACT
		curveService.update(updatedCurvePoint);
		
		// ASSERT
		verify(curvePointRepository, times(1)).getReferenceById(updatedCurvePoint.getId());
		verify(curvePointRepository, times(1)).save(any(CurvePoint.class));
	}
	
	@Test
	public void deleteCurvePointTest() {
		// ARRANGE
		when(curvePointRepository.getReferenceById(any(Integer.class))).thenReturn(curvePoint);
		doNothing().when(curvePointRepository).deleteById(any(Integer.class));
		
		// ACT
		curveService.deleteCurvePoint(curvePoint.getId());
		
		// ASSERT
		verify(curvePointRepository, times(1)).getReferenceById(curvePoint.getId());
		verify(curvePointRepository, times(1)).deleteById(curvePoint.getId());
	}
}