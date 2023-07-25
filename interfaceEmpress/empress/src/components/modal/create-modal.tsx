import { useEffect, useState } from "react";
import { useProdutoData } from "../../hooks/useProdutoData";
import { useProdutoDataMutation } from "../../hooks/useProdutoDataMutation";
import { ProdutoData } from "../../interface/ProdutoDate";
import "./modal.css";

interface InputProps<T> {
    label: string;
    value: T;
    updateValue: (value: T) => void;
}

const Input = <T extends string | number>({ label, value, updateValue }: InputProps<T>) => {
    return (
        <div>
            <label>{label}</label>
            <input value={value} onChange={event => updateValue(event.target.value as T)} />
        </div>
    );
}


interface CreatemodalProps {
    closeModal: () => void;
}

export function Createmodal({ closeModal }: CreatemodalProps) {
    const [nome, setNome] = useState("");
    const [valor, setValor] = useState(0);
    const [imagem, setImagem] = useState("");
    const { mutate, isSuccess,isLoading } = useProdutoDataMutation();

    const submit = () => {
        const produtoData: ProdutoData = {
            nome,
            valor,
            imagem,
        }
        mutate(produtoData);
    }

    useEffect(() => {
        if(!isSuccess) return;
        closeModal();
    }, [isSuccess]);

    return (
        <div className="modal-overflow">
            <div className="modal-body">
                <h2>Cadastro de produto</h2>
                <form className="input-container">
                    <Input label="Nome" value={nome} updateValue={setNome} />
                    <Input label="Valor" value={valor} updateValue={setValor} />
                    <Input label="Imagem" value={imagem} updateValue={setImagem} />
                </form>
                <button onClick={submit} className="btn-secondary">
                {isLoading? 'Cadastrando...': 'Cadastrar'}
                </button>
            </div>
        </div>
    )
}
