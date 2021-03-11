package com.jsimplec.prms.service.impl;

import com.jsimplec.prms.dto.prplan.PrPlanRequestDTO;
import com.jsimplec.prms.dto.prplan.PrPlanResponseDTO;
import com.jsimplec.prms.errors.GenericError;
import com.jsimplec.prms.mapper.PrPlanMapper;
import com.jsimplec.prms.model.PrPlanModel;
import com.jsimplec.prms.repository.PrPlanRepository;
import com.jsimplec.prms.service.PrPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.jsimplec.prms.errors.ErrorDefinition.NOT_IMPLEMENTED;
import static com.jsimplec.prms.errors.ErrorDefinition.PR_PLAN_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class PrPlanServiceImpl implements PrPlanService {

    private final PrPlanRepository planRepository;
    private final PrPlanMapper planMapper;

    @Override
    public PrPlanResponseDTO addPlan(PrPlanRequestDTO req) {
        if (planRepository.existsByName(req.getName())) {
            throw new GenericError(PR_PLAN_ALREADY_EXISTS, req.getName());
        }
        PrPlanModel prModelToSave = planMapper.requestToModel(req);
        PrPlanModel savedPrModel = planRepository.save(prModelToSave);
        return planMapper.modelToResponse(savedPrModel);
    }

    @Override
    public List<PrPlanResponseDTO> getAllActivePlans() {
        return planRepository
                .findAllByIsActiveTrue()
                .stream()
                .map(planMapper::modelToResponse)
                .collect(Collectors.toList());
    }

}
