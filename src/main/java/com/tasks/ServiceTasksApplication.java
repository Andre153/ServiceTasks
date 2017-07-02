package com.tasks;

import com.tasks.domain.CoreUser;
import com.tasks.domain.Task;
import com.tasks.repository.CoreUserRepository;
import com.tasks.repository.TasksRepository;
import com.tasks.resources.CoreUserResource;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ServiceTasksApplication extends Application<ServiceTasksConfiguration>{

    public static void main(final String[] args) throws Exception {
        new ServiceTasksApplication().run(args);
    }

    private final HibernateBundle<ServiceTasksConfiguration> hibernateBundle =
            new HibernateBundle<ServiceTasksConfiguration>(CoreUser.class, Task.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(ServiceTasksConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public String getName() {
        return "ServiceTasks";
    }

    @Override
    public void initialize(final Bootstrap<ServiceTasksConfiguration> bootstrap) {

        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );

        bootstrap.addBundle(new MigrationsBundle<ServiceTasksConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(ServiceTasksConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });

        bootstrap.addBundle(hibernateBundle);

    }

    @Override
    public void run(final ServiceTasksConfiguration configuration,
                    final Environment environment) {
        final CoreUserRepository coreUserRepository = new CoreUserRepository(hibernateBundle.getSessionFactory());
        final TasksRepository tasksRepository = new TasksRepository(hibernateBundle.getSessionFactory());

        CoreUserResource coreUserResource = new CoreUserResource(coreUserRepository, tasksRepository);
        environment.jersey().register(coreUserResource);
    }


}
