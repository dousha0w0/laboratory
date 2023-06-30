package com.senior.service.utils;

import com.aliyun.facebody20191230.models.CompareFaceResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.tea.TeaModel;
import com.aliyun.teautil.Common;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;

public class FaceUtil {
    public static com.aliyun.facebody20191230.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "facebody.cn-shanghai.aliyuncs.com";
        return new com.aliyun.facebody20191230.Client(config);
    }

    private static String getCompareDataJson(File a, File b) throws Exception {
        com.aliyun.facebody20191230.Client client = FaceUtil.createClient("LTAI5tCzz53AS2ppJeYRbafK", "dDuB19uNeLkGPxgPCrXNTh6syLsIla");
        InputStream inputStreamA = Files.newInputStream(a.toPath());
        InputStream inputStreamB = Files.newInputStream(b.toPath());
        com.aliyun.facebody20191230.models.CompareFaceAdvanceRequest compareFaceAdvanceRequest = new com.aliyun.facebody20191230.models.CompareFaceAdvanceRequest().setImageURLAObject(inputStreamA).setImageURLBObject(inputStreamB);
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            CompareFaceResponse compareFaceResponse = client.compareFaceAdvance(compareFaceAdvanceRequest, runtime);

            // 获取整体结果
            return Common.toJSONString(TeaModel.buildMap(compareFaceResponse));
        } catch (TeaException teaException) {
            // 获取整体报错信息
            System.out.println(Common.toJSONString(teaException));
            // 获取单个字段
            System.out.println(teaException.getCode());
        }
        return null;
    }

    @SneakyThrows
    public static boolean isSame(File a, File b) {
        String json = getCompareDataJson(a, b);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        JsonObject bodyObject = jsonObject.getAsJsonObject("body");
        JsonObject dataObject = bodyObject.getAsJsonObject("Data");
        double confidence = dataObject.get("Confidence").getAsDouble();
        return confidence >= 75.0;
    }

}
