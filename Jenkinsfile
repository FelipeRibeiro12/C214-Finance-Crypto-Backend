pipeline {
    agent any

    environment {
        APP_NAME    = 'finance-crypto-backend'
        DB_URL      = credentials('DB_URL')
        DB_USERNAME = credentials('DB_USERNAME')
        DB_PASSWORD = credentials('DB_PASSWORD')
    }

    stages {

        // =========================================================
        // STAGE 1 — comitado por Felipe
        // Responsabilidade: compilar o projeto e garantir que o
        // build está funcionando antes de qualquer outra etapa.
        // =========================================================
        stage('Build') {
            steps {
                echo '🔨 Compilando o projeto...'
                sh './mvnw clean compile -q'
            }
        }

        // =========================================================
        // STAGE 2 — comitado por Colega A
        // Responsabilidade: executar todos os testes unitários e
        // publicar o relatório XML do Surefire no Jenkins.
        // =========================================================
        stage('Testes Unitários') {
            steps {
                echo '🧪 Executando testes unitários...'
                sh './mvnw test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
                failure {
                    echo '❌ Testes falharam — verificar relatório Surefire.'
                }
            }
        }

        // =========================================================
        // STAGE 3 — comitado por Colega B
        // Responsabilidade: gerar o relatório de cobertura JaCoCo
        // e validar que a cobertura mínima de 60% foi atingida.
        // =========================================================
        stage('Cobertura de Código') {
            steps {
                echo '📊 Gerando relatório de cobertura JaCoCo...'
                sh './mvnw verify -DskipTests=false'
            }
            post {
                always {
                    jacoco(
                        execPattern:      'target/jacoco.exec',
                        classPattern:     'target/classes',
                        sourcePattern:    'src/main/java',
                        exclusionPattern: '**/Startup.class,**/dto/**,**/entity/**'
                    )
                }
            }
        }

        // =========================================================
        // STAGE 4 — comitado por Felipe
        // Responsabilidade: construir a imagem Docker da aplicação
        // para garantir que o Dockerfile está funcional.
        // =========================================================
        stage('Docker Build') {
            steps {
                echo '🐳 Construindo imagem Docker...'
                sh "docker build -t ${APP_NAME}:${BUILD_NUMBER} ."
                sh "docker tag ${APP_NAME}:${BUILD_NUMBER} ${APP_NAME}:latest"
                echo "✅ Imagem ${APP_NAME}:${BUILD_NUMBER} criada com sucesso."
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo '✅ Pipeline concluída com sucesso!'
        }
        failure {
            echo '❌ Pipeline falhou. Verifique os logs acima.'
        }
    }
}
