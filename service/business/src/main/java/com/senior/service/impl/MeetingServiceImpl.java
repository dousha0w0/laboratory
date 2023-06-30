package com.senior.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import com.senior.service.utils.FaceUtil;
import com.senior.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.senior.common.kits.BeanCopierKits;
import com.senior.common.kits.PreconditionsKits;
import com.senior.dao.MeetingDao;
import com.senior.domain.bo.request.MeetingCreateBoRequest;
import com.senior.domain.bo.request.MeetingQueryBoRequest;
import com.senior.domain.bo.request.MeetingUpdateBoRequest;
import com.senior.domain.bo.response.MeetingQueryBoResponse;
import com.senior.domain.model.MeetingDo;
import com.senior.service.MeetingService;

import lombok.extern.slf4j.Slf4j;

/**
 * 实验室(Meeting) service接口实现类
 *
 * @author senior
 * @since 2021-08-17 15:09:50
 */
@Slf4j
@Service
public class MeetingServiceImpl implements MeetingService {
    public static final int SUCCESS = 1;

    @Resource
    private MeetingDao dao;

    @Resource
    private AccountDao accountDao;

    private final RedisTemplate redisTemplate;

    public MeetingServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void decryptByBase64(String base64, String filePath) {
        if (base64 == null && filePath == null) {
            return;
        }
        try {
            if (base64 != null) {
                byte[] decodedBytes = Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
                Files.write(Paths.get(filePath), decodedBytes, StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public boolean create(MeetingCreateBoRequest request) throws IOException {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        String faceImage = request.getFaceImage();
        List<Long> accountIds = request.getAccountIds();
        String baseDirectory = "faces" + File.separator;
        File filea = new File(baseDirectory + UUID.randomUUID().toString().replace("-", "") + ".jpg");
        File fileb = new File(baseDirectory + UUID.randomUUID().toString().replace("-", "") + ".jpg");
        for (Long accountId : accountIds) {
            if (!Files.exists(filea.toPath())) {
                Files.createDirectories(filea.getParentFile().toPath());
                Files.createFile(filea.toPath());
            }
            if (!Files.exists(fileb.toPath())) {
                Files.createDirectories(fileb.getParentFile().toPath());
                Files.createFile(fileb.toPath());
            }
            String faceImage1 = accountDao.getById(accountId).getFaceImage();
            decryptByBase64(faceImage1, filea.getPath());
            decryptByBase64(faceImage, fileb.getPath());
            //根据user对比人脸
            try {
                if (!FaceUtil.isSame(filea, fileb)) {
                    return false;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
        filea.delete();
        fileb.delete();
        //使用redis将人脸信息存储起来
        String json = "{\"faceImage1\":\"" + request.getFaceImage() + "\",\"faceImage2\":\"" + request.getFaceImage() + "\"}";
        // 获取 Set 对象
        SetOperations<Serializable, String> setOperations = redisTemplate.opsForSet();
        // 将 JSON 字符串添加到 Set 中
        setOperations.add("faceset", json);
        MeetingDo model = BeanCopierKits.copyProperties(request, MeetingDo.class);
        request.setId(model.getId());
        return dao.insert(model) == SUCCESS;
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        return dao.deleteById(id) == SUCCESS;
    }

    @Transactional
    @Override
    public boolean updateById(MeetingUpdateBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.updateById(BeanCopierKits.copyProperties(request, MeetingDo.class)) == SUCCESS;
    }

    @Override
    public MeetingQueryBoResponse getById(Long id) {
        PreconditionsKits.checkNotNull(id, "id不能为空");
        return BeanCopierKits.copyProperties(dao.getById(id), MeetingQueryBoResponse.class);
    }

    @Override
    public List<MeetingQueryBoResponse> getByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return Lists.newArrayList();
        }
        return BeanCopierKits.copyProperties(dao.getByIds(ids), MeetingQueryBoResponse.class);
    }

    @Override
    public List<MeetingQueryBoResponse> queryList(MeetingQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.queryList(request);
    }

    @Override
    public List<MeetingQueryBoResponse> groupBy(MeetingQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.groupBy(request);
    }

    @Override
    public long queryCount(MeetingQueryBoRequest request) {
        PreconditionsKits.checkNotNull(request, "参数不能为空");
        return dao.queryCount(request);
    }
}
