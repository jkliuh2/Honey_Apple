package com.honeyapple.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileManagerService {

	// 학원용
//	public static final String FILE_UPLOAD_PATH = "D:\\SEOJAVA\\7_honeyapple\\HoneyApple\\workspace\\images/";
	// 집 용
	public static final String FILE_UPLOAD_PATH = "C:\\Users\\appli\\OneDrive\\Desktop\\6Week\\JAVA\\7_Honey_Apple\\honey_apple\\workspace\\images/";

	// input:Flie 원본, userLoginId(폴더명) / output: 이미지 경로
	public String saveFile(String loginId, MultipartFile file) {

		// 1. 폴더(디렉토리) 생성
		// 폴더 명
		String directoryName = loginId + "_" + System.currentTimeMillis();
		// 예: aaaa_28952589028

		// 폴더의 전체 경로
		String filePath = FILE_UPLOAD_PATH + directoryName;
		// "D:\\SEOJAVA\\6_spring_project\\MEMO\\memo_workspace\\images/aaaa_28952589028"

		// 실제 폴더 디렉토리 생성
		File directory = new File(filePath);
		if (directory.mkdir() == false) {
			// 폴더 생성 실패 시 이미지 경로 null 리턴
			return null;
		}

		// 2. 파일 업로드: byte 단위로 업로드
		try {
			byte[] bytes = file.getBytes(); 
			// ★★주의★★ get()이 한글 이미지 인식이 안됨. => 나중에 영문자로 바뀌서 올리기
			// getOriginalFilename(): 원본 파일의 이름값 -> 이게 한글이면 곤란
			Path path = Paths.get(filePath + "/" + file.getOriginalFilename()); // path 설정
			Files.write(path, bytes); // 실제 파일 업로드
		} catch (IOException e) {
			e.printStackTrace();
			return null; // 이미지 업로드 실패시 null 리턴
		}

		// 3. WAS에 연결
		// 파일 업로드가 성공했으면 웹 이미지 url path를 리턴
		return "/images/" + directoryName + "/" + file.getOriginalFilename();
	}

	// 이미지 삭제 메소드
	// input: imagePath / output:X
	public void deleteFile(String imagePath) {
		// 실제 파일 경로: "D:\\SEOJAVA\\6_spring_project\\MEMO\\memo_workspace\\images/"
		// + "/images/aaaa_1705569414724/리다이렉트.png"

		// 주소에 겹치는 /images/ 지운다. -> imagePath의 문자열 변환
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));

		// 삭제할 이미지가 존재하는가?
		if (Files.exists(path)) {
			try {
				Files.delete(path); // 파일 삭제
			} catch (IOException e) {
				log.info("[파일매니저 삭제] 이미지 삭제 실패. path:{}", path.toString());
				return; // 삭제가 잘못될 경우, 중단하고 리턴
			}

			// 디렉토리(폴더) 삭제
			path = path.getParent(); // 파일경로의 "부모" 경로 -> 자동으로 폴더주소
			if (Files.exists(path)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					log.info("[파일매니저 삭제] 이미지 삭제 실패. path:{}", path.toString());
					// 폴더 삭제 실패시.
					// 마지막 단계라서 return은 하지 않는다.
				}
			}
		}
	}
}