package com.study.spring.service;

import com.study.spring.domain.SkillCard;
import com.study.spring.repository.MemberRepository;
import com.study.spring.repository.CommonRepository;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class CommonService {

    private final CommonRepository commonRepository;
    private final MemberRepository memberRepository;

    public CommonService(CommonRepository commonRepository, MemberRepository memberRepository) {
        this.commonRepository = commonRepository;
        this.memberRepository = memberRepository;
    }

    /*
     *
     * file.getInputStream() 으로 OPCPackage 객체로 변환 후 XSSFWorkbook 객로 변환
     *  ROW (세로) cell (가로)
     * sheet.getPhysicalNumberOfRows() 세로의 총 길이를 알 수 있음
     * setFild() 따로 분리해둔 메소드 를 이용해 POJO 객체를 만든다.
     *
     * */
    public void fileUpload(MultipartFile file) {
        //        List<Fruit> list = new ArrayList<Fruit>();
        try {
            OPCPackage opcPackage = OPCPackage.open(file.getInputStream());
            XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);

            // 첫번째 시트 불러오기
            XSSFSheet sheet = workbook.getSheetAt(0);

            List<SkillCard> skillCardList = new ArrayList<>();

            // dbName 과 pk 값
            String dbName = sheet.getRow(0).getCell(0).toString();
            String pk = sheet.getRow(1).getCell(0).toString();

            // getPhysicalNumberOfRows() : row 최대 값

            for (int i = 4; i < sheet.getPhysicalNumberOfRows(); i++) {

                SkillCard skillCard = setFild(sheet, sheet.getRow(i));
                skillCardList.add(skillCard);
            }

            commonRepository.insertDbData(skillCardList, dbName, pk);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *
     * getPhysicalNumberOfCells() row의 각 가로의 값을 알 수 있음
     * ReflectionUtils.findField(POJO클래스, 셀 위치) 메소드를 이용 Field개체로 변환
     * ReflectionUtils.makeAccessible(Field 객체) 메소드를 이용 private 객체에 접근
     * ReflectionUtils.setField(Field 객체, POJO클래스 객체 , 셀 위치) 메소드를 이용하여 setter와 같은 기능 수행
     *
     * switch 문으로 각 데이터타입에 대응
     * */
    private SkillCard setFild(XSSFSheet row, XSSFRow value) {

        SkillCard skillCard = new SkillCard();

        //  getPhysicalNumberOfCells() : cell 최대값
        for (int j = 0; j < row.getRow(2).getPhysicalNumberOfCells(); j++) {

            Field field = ReflectionUtils.findField(SkillCard.class, row.getRow(3).getCell(j).toString());
            ReflectionUtils.makeAccessible(field);

            switch (row.getRow(2).getCell(j).toString()) {

            case "String":
                ReflectionUtils.setField(field, skillCard, value.getCell(j).toString());
                break;
            case "int":
                ReflectionUtils.setField(field, skillCard, (int) Math.floor(Double.parseDouble(value.getCell(j).toString())));
                break;
            }
        }
        return skillCard;
    }

    public String createJwt(String uid) throws Exception {
        return commonRepository.createJwt(memberRepository.findUserToUid(uid).getUid());
    /*
        jwt토큰
            return Jwts.builder()
                    .setSubject(id)
                    .signWith(signatureAlgorithm, getSecretKeySpec(DatatypeConverter.parseBase64Binary(components.getDefaultData().getSecretKey())))
                    .setExpiration(new Date(System.currentTimeMillis() + 1 * 1000 * 60 * 60 * 128))
                    .compact();
        }
    */
    }

    public String getJwt(String idToken) throws Exception {
        return commonRepository.getJwt(idToken);
    /*
        jwt
        try {
            return Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary("secretKey"))
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("parseError : " + e.getMessage());
        }
     */
    }
}