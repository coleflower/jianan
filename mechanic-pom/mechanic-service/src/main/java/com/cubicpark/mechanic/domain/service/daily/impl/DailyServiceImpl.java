package com.cubicpark.mechanic.domain.service.daily.impl;

import com.cubicpark.mechanic.dao.daily.IDailyDAO;
import com.cubicpark.mechanic.domain.dto.daily.DailyDTO;
import com.cubicpark.mechanic.domain.service.daily.IDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * 〈一句话功能简述〉<br>
 * 日报管理impl
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class DailyServiceImpl implements IDailyService {

    @Autowired
    private IDailyDAO dailyDAO;

    @Override
    public int insert(DailyDTO dailyDTO) {
        dailyDTO.setCreateDate(new Timestamp(System.currentTimeMillis()));
        int row = dailyDAO.insert(dailyDTO);
        return row;
    }

    @Override
    public DailyDTO selectById(Integer id) {
        DailyDTO dailyDTO = dailyDAO.selectById(id);
        return dailyDTO;
    }

    @Override
    public List<DailyDTO> selectToday() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = simpleDateFormat.format(date);
        List<DailyDTO> dailyDTOList = dailyDAO.selectToday(date1);
        return dailyDTOList;
    }

    @Override
    public DailyDTO selectTodayByEmployeeCode(String employeeCode) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = simpleDateFormat.format(date);
        DailyDTO dailyDTO = dailyDAO.selectTodayByEmployeeCode(employeeCode,date1);
        return dailyDTO;
    }

    //查找一个月内的工单
    public List<DailyDTO> findOneMouthBefore(String employeeCode) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String oneMouthBefore = format.format(m);
        Date date = null;
        try {
            date = format.parse(oneMouthBefore);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dailyDAO.selectAllOneMouthBeforeOnMyself(date,employeeCode);
    }

    @Override
    public List<String> selectCodeLike(String code) {
        return dailyDAO.selectCodeLike(code);
    }

    public List<DailyDTO> selectMyselfByDate(String employeeCode,String startTime){
        return dailyDAO.selectMyselfByDate(employeeCode,startTime);
    }

    @Override
    public List<DailyDTO> selectByDateAndCodeLike(String time, String code) {
        return dailyDAO.selectByDateAndCodeLike(time,code);
    }

}
