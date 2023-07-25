import { useState, useEffect } from 'react';
import { ProdutoData } from '../interface/ProdutoDate';
import  API  from '../service/API';



export function useProdutoData(page : number) {
  const [data, setData] = useState<ProdutoData[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchProdutoData() {
      try {
        setLoading(true);
        const response = await API.get<ProdutoData[]>('/products',{
            params: {
                page: page,
                size: 6
            }
        });
        setData(response.data);
      } catch (error: unknown) { // Utilizamos a declaração de tipo "unknown" aqui para o objeto "error"
        console.error('Erro ao buscar produtos:', error);
      } finally {
        setLoading(false);
      }
    }

    fetchProdutoData();
  }, []);

  return { data, loading };
}
