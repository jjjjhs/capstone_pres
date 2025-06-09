import React from 'react';

export default function Header({ title, onClose }) {
  return (
    <header style={{
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'space-between',
      padding: '8px 16px',
      borderBottom: '1px solid #ddd',
      background: '#fff'
    }}>
      <h2 style={{ margin: 0, fontSize: '16px', fontWeight: '500' }}>
        {title}
      </h2>
      <button
        onClick={onClose}
        style={{
          border: 'none',
          background: 'transparent',
          fontSize: '18px',
          cursor: 'pointer',
          lineHeight: 1
        }}
      >
        ×
      </button>
    </header>
  );
}
