package com.cubicpark.mechanic.domain.service.assessor.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cubicpark.mechanic.dao.assessor.IAssessorAssistantDAO;
import com.cubicpark.mechanic.domain.dto.assessor.AssessorAssistantDTO;
import com.cubicpark.mechanic.domain.service.assessor.IAssessorAssistantService;
import org.springframework.stereotype.Service;

@Service
public class AssessorAssistantServiceImpl extends ServiceImpl<IAssessorAssistantDAO,AssessorAssistantDTO> implements IAssessorAssistantService {
}
