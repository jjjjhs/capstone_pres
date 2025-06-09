import React from 'react';

export default function Button({ children, onClick, style }) {
  return (
    <button
      onClick={onClick}
      style={{
        padding: '6px 12px',
        border: '1px solid #333',
        background: '#fff',
        cursor: 'pointer',
        ...style
      }}
    >
      {children}
    </button>
  );
}
