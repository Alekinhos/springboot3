import React, { useState, useEffect } from 'react';
import './App.css';
import { Card } from './components/cards/card';
import { useProdutoData } from './hooks/useProdutoData';
import { Createmodal } from './components/modal/create-modal';
import API from './service/API';
import Paginator from './components/paginator/paginator';

function App() {
  const [currentPage, setCurrentPage] = useState(0);
  const { data, loading } = useProdutoData(currentPage);
  const [totalPages, setTotalPages] = useState(0); // Estado para armazenar o número total de páginas
  const [isModalOpen, setIsModalOpen] = useState(false);

  const pageSize = 6; // Defina o tamanho da página (quantidade de produtos por página)

  useEffect(() => {
    async function fetchTotalPages() {
      try {
        const response = await API.get('/products'); 
        console.log(response.headers)
        setTotalPages(response.headers['x-total-pages']); 
      } catch (error) {
        console.error('Erro ao buscar o número total de páginas:', error);
      }
    }

    fetchTotalPages();
  }, []);


  const handleOpenModal = () => {
    setIsModalOpen(prev => !prev);
  }

  const handlePageChange = (page: number) => {
    setCurrentPage(page - 1); // Subtraímos 1 porque a API usa índices baseados em zero para a paginação
  }

  // Filtra os produtos com base na página atual
  const currentProducts = data?.slice(currentPage * pageSize, (currentPage + 1) * pageSize);

  if (loading) {
    // Se estiver carregando os dados, exibe uma mensagem de carregamento
    return <div>Carregando...</div>;
  }

  return (
    <div className="container">
      <h1>Produtos</h1>
      <div className="card-grid">
        {currentProducts?.map(produto => (
          <Card
            key={produto.id} // Usamos a propriedade "id" como chave do card
            valor={produto.valor}
            nome={produto.nome}
            imagem={produto.imagem}
          />
        ))}
        {isModalOpen && <Createmodal closeModal={handleOpenModal} />}
      </div>
      <button onClick={handleOpenModal} className="btn-primary">Novo</button>

      <Paginator
        currentPage={currentPage}
        totalPages={totalPages}
        onPageChange={handlePageChange}
      />
    </div>
  );
}

export default App;
