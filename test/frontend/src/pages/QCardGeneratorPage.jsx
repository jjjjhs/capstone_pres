// src/pages/QCardGeneratorPage.jsx
import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import Header from '../components/Header'
import Button from '../components/Button'
import sampleSlide from '../assets/slide.png'

export default function QCardGeneratorPage() {
  const navigate = useNavigate()
  const [showResult, setShowResult] = useState(false)

  const handleGenerate = () => {
    // TODO: 실제 API 호출 → setCards(res.data)
    setShowResult(true)
  }

  const handleBack = () => {
    setShowResult(false)
  }

  const handlePractice = () => {
    navigate('/practice')
  }

  return (
    <div style={{
      display: 'flex',
      flexDirection: 'column',
      height: '100vh'
    }}>
      <Header
        title="캡스톤디자인 > 중간발표"
        onClose={() => {}}
      />

      {showResult ? (
        // ─── 결과 화면 ───────────────────────────────
        <>
          <div style={{
            flex: 1,
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            background: '#fafafa'
          }}>
            <h2>큐카드 생성 결과</h2>
          </div>

          <div style={{
            padding: 16,
            display: 'flex',
            justifyContent: 'center',
            gap: 16
          }}>
            <Button onClick={handleBack}>뒤로</Button>
            <Button onClick={handlePractice}>연습하기</Button>
          </div>
        </>
      ) : (
        // ─── 슬라이드 + 버튼 화면 ─────────────────────
        <>
          <div style={{
            flex: 1,
            background: '#f5f6f7',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            overflow: 'auto'
          }}>
            <img
              src={sampleSlide}
              alt="슬라이드 예시"
              style={{ maxWidth: '90%', maxHeight: '90%' }}
            />
          </div>

          <div style={{
            padding: 16,
            display: 'flex',
            justifyContent: 'center'
          }}>
            <Button onClick={handleGenerate}>
              큐카드 생성
            </Button>
          </div>
        </>
      )}
    </div>
  )
}
