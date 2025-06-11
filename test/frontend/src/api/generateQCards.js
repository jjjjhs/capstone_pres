// src/api/generateQCards.js
import axios from 'axios';

/**
 * 발표자료 이미지를 보내면 큐카드 목록을 돌려받는 가상의 API 호출 함수
 * @param {File|Blob} imageBlob 
 * @returns Promise<{ data: Array<{title:string, content:string}> }>
 */
export async function generateQCards(imageBlob) {
  const form = new FormData();
  form.append('slide', imageBlob, 'slide.png');
  const res = await axios.post(
    'http://localhost:8000/api/qcards', //fast api 호출
    form,
    { headers: { 'Content-Type': 'multipart/form-data' } }
  );
  return res.data.data;
}
