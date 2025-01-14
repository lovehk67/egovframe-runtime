/*
 * Copyright 2006-2007 the original author or authors. *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.egovframe.brte.sample.example.test;

import org.egovframe.brte.sample.common.domain.trade.CustomerCredit;
import org.egovframe.brte.sample.testcase.test.EgovAbstractIoSampleTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * 이벤트 알림 템플릿 관리를 수행하는 테스트
 * 결과는 실제 이메일 수신여부로 확인할 것 
 * @author 배치실행개발팀
 * @since 2012. 06.27
 * @version 1.0
 * @see <pre>
 *      개정이력(Modification Information)
 *   
 *   수정일      수정자           수정내용
 *  ------- -------- ---------------------------
 *  2012.06.27  배치실행개발팀     최초 생성
 *  </pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/org/egovframe/batch/jobs/eventNoticeTriggerJob.xml"})
public class EgovEventNoticeTriggerFunctionalTests extends EgovAbstractIoSampleTests {

	//배치작업을  test하기 위한 JobLauncherTestUtils
	@Autowired
	@Qualifier("jobLauncherTestUtils")
	private JobLauncherTestUtils jobLauncherTestUtils;

	/**
	 * 배치 작업 테스트 
	 */
	@Override
	@Test
	public void testUpdateCredit() throws Exception {

		JobExecution jobExecution = jobLauncherTestUtils.launchJob(getUniqueJobParameters());
		/*
		 * 테스트의 결과가 아닌 
		 * 실제 웹페이지에서 메일 수신여부로 성공여부 판단 할 것
		 */
		assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());

	}

	/**
	 * 배치결과를 다시 읽을 때  reader 설정하는 메소드
	 */
	@Override
	protected void pointReaderToOutput(ItemReader<CustomerCredit> reader) {
	}

	/**
	 * 잡파라미터를 설정하기 위한 메소드 
	 * @return jobParameters
	 */
	@Override
	protected JobParameters getUniqueJobParameters() {
		return new JobParametersBuilder(super.getUniqueJobParameters())
				.addString("inputFile", "/org/egovframe/data/input/delimited.csv")
				.addString("outputFile", "file:./target/test-outputs/delimitedOutput.csv")
				.toJobParameters();
	}

}