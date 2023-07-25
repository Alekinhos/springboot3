import React from "react";
import "./card.css"; // Verifique o caminho correto para o arquivo "card.css"

interface CardProps {
  codigo?: string;
  valor: number;
  nome: string;
  imagem: string;
}

export function Card({ codigo, valor, nome, imagem }: CardProps) {
  return (
    <div className="card">
      <img src={imagem} alt={nome} />
      <h2>{nome}</h2>
      <p>
        <b>Valor:</b> {valor}
      </p>
    </div>
  );
}
